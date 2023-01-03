package com.sigma.software.test.demo.dto;

public class StatisticsResponseDTO {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private Long count;

    public StatisticsResponseDTO(String sum, String avg, String max, String min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public String getAvg() {
        return avg;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public Long getCount() {
        return count;
    }
}
