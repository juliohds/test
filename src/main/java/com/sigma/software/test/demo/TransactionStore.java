package com.sigma.software.test.demo;

import com.sigma.software.test.demo.domain.Statistics;
import com.sigma.software.test.demo.domain.Transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TransactionStore {
    private final int MAX_TRANSACTIONS = 60;
    private final long ONE_MINUTE_IN_MILLISECONDS = 60000;
    private List<Transaction> transactions;
    private Statistics statistics;

    public TransactionStore() {
        transactions = new ArrayList<>();
        statistics = new Statistics();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        statistics.update(transaction);
        removeExpiredTransactions();
    }

    public void deleteTransactions() {
        transactions.clear();
        statistics.reset();
    }

    public Statistics getStatistics() {
        removeExpiredTransactions();
        return statistics;
    }

    private void removeExpiredTransactions() {
        Instant now = Instant.now();
        List<Transaction> expiredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Instant timestamp = transaction.getTimestamp();
            if (now.toEpochMilli() - timestamp.toEpochMilli() > ONE_MINUTE_IN_MILLISECONDS) {
                expiredTransactions.add(transaction);
                statistics.removeTransaction(transaction);
            }
        }
        transactions.removeAll(expiredTransactions);
    }
}
