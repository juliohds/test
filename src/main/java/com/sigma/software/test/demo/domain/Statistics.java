package com.sigma.software.test.demo.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Statistics {
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public Statistics() {
        reset();
    }

    public void update(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        sum = sum.add(amount);
        max = (max.compareTo(amount) > 0) ? max : amount;
        min = (min.compareTo(amount) < 0) ? min : amount;
        count++;
        avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    public void removeTransaction(Transaction transaction){
        BigDecimal amount = transaction.getAmount();
        sum = sum.subtract(amount);
        max = max.equals(amount) ? BigDecimal.ZERO : max;
        min = min.equals(amount) ? new BigDecimal(999999999) : min;
        count--;
        avg = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }
    public void reset() {
        sum = BigDecimal.ZERO;
        avg = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = new BigDecimal(999999999);
        count = 0;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }
}
