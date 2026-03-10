package com.erick.minibankingmicroservice.controller;

import com.erick.minibankingmicroservice.dto.DepositReq;
import com.erick.minibankingmicroservice.dto.TransferReq;
import com.erick.minibankingmicroservice.dto.WithdrawReq;
import com.erick.minibankingmicroservice.response.ApiResponse;
import com.erick.minibankingmicroservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/banking/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ApiResponse<?> deposit(@Valid @RequestBody DepositReq req) {
        transactionService.deposit(req.getAccountNumber(), req.getAmount());
        return new ApiResponse<>(
                true,
                "Deposit successful",
                null,
                LocalDateTime.now()
        );
    }

    @PostMapping("/withdraw")
    public ApiResponse<?> withdraw(@Valid @RequestBody WithdrawReq req) {

        transactionService.withdraw(
                req.getAccountNumber(),
                req.getAmount()
        );

        return new ApiResponse<>(
                true,
                "Withdraw successful",
                null,
                LocalDateTime.now()
        );
    }

    @PostMapping("/transfer")
    public ApiResponse<?> transfer(@Valid @RequestBody TransferReq req) {

        transactionService.transfer(
                req.getFromAccountNumber(),
                req.getToAccountNumber(),
                req.getAmount(),
                req.getTransferType()
        );

        return new ApiResponse<>(
                true,
                "Transfer successful",
                null,
                LocalDateTime.now()
        );
    }

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> exportExcel(
            @RequestParam(required = false) String branchId,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        ByteArrayInputStream excel = transactionService.getTransactionForReport(branchId,
                transactionType, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=transactions.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excel));
    }


}
