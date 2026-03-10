package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.TransactionReport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelReportService {

    ByteArrayInputStream generateExcel(List<TransactionReport> data);
}
