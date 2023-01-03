package com.sigma.software.test.demo.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import javax.validation.Valid;

import com.sigma.software.test.demo.domain.Statistics;
import com.sigma.software.test.demo.domain.Transaction;
import com.sigma.software.test.demo.TransactionStore;
import com.sigma.software.test.demo.dto.StatisticsResponseDTO;
import com.sigma.software.test.demo.dto.TransactionRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {
    private TransactionStore transactionStore;

    public TransactionsController() {
        transactionStore = new TransactionStore();
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> addTransaction(@Valid @RequestBody TransactionRequestDTO request) {
        try {
            BigDecimal amount = new BigDecimal(request.getAmount());
            Instant timestamp = Instant.parse(request.getTimestamp());
            if (timestamp.isAfter(Instant.now())) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            Duration duration = Duration.between(timestamp, Instant.now());
            if (duration.getSeconds() > 60) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Transaction transaction = new Transaction(amount, timestamp);
            transactionStore.addTransaction(transaction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NumberFormatException | DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        Statistics statistics = transactionStore.getStatistics();
        StatisticsResponseDTO response = new StatisticsResponseDTO(
                statistics.getSum().toPlainString(),
                statistics.getAvg().toPlainString(),
                statistics.getMax().toPlainString(),
                statistics.getMin().toPlainString(),
                statistics.getCount()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<Void> deleteTransactions() {
        transactionStore.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
