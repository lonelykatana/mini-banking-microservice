package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.TransactionReport;
import com.erick.minibankingmicroservice.exception.ApiException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
public class ExcelReportServiceImpl implements ExcelReportService {
    @Override
    public ByteArrayInputStream generateExcel(List<TransactionReport> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Transactions");

            AtomicInteger rowIndex = new AtomicInteger(0);

            Row dateRow = sheet.createRow(rowIndex.getAndIncrement());
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            dateRow.createCell(0)
                    .setCellValue("Date: " +
                                  LocalDate.now().format(formatter));

            rowIndex.incrementAndGet();

            String[] headers = {
                    "Source Account",
                    "Target Account",
                    "Transaction Type",
                    "Amount",
                    "Branch ID",
                    "Branch Name",
                    "Created At"
            };

            Row headerRow = sheet.createRow(rowIndex.getAndIncrement());

            IntStream.range(0, headers.length)
                    .forEach(i ->
                            headerRow.createCell(i)
                                    .setCellValue(headers[i])
                    );

            data.forEach(tx -> {
                Row row = sheet.createRow(rowIndex.getAndIncrement());
                row.createCell(0).setCellValue(tx.getSourceAccountNumber());
                row.createCell(1).setCellValue(tx.getTargetAccountNumber());
                row.createCell(2).setCellValue(tx.getTransactionType());
                row.createCell(3).setCellValue(tx.getAmount().doubleValue());
                row.createCell(4).setCellValue(tx.getBranchId());
                row.createCell(5).setCellValue(tx.getBranchName());
                row.createCell(6).setCellValue(tx.getCreatedAt().format(dateTimeFormatter));
            });

            // Auto resize columns
            IntStream.range(0, headers.length)
                    .forEach(sheet::autoSizeColumn);

            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            throw new ApiException("Failed to create excel report");
        }

        return new ByteArrayInputStream(out.toByteArray());

    }
}
