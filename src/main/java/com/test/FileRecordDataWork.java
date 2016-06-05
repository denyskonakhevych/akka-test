package com.test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class FileRecordDataWork {

    private ConcurrentMap<String, AtomicInteger> aggregatedResult;
    private String[] row;
    private AtomicInteger counter;

    public FileRecordDataWork() {
    }

    public FileRecordDataWork(ConcurrentMap<String, AtomicInteger> aggregatedResult, String[] row, AtomicInteger counter) {
        this.aggregatedResult = aggregatedResult;
        this.row = row;
        this.counter = counter;
    }

    public ConcurrentMap<String, AtomicInteger> getAggregatedResult() {
        return aggregatedResult;
    }

    public void setAggregatedResult(ConcurrentMap<String, AtomicInteger> aggregatedResult) {
        this.aggregatedResult = aggregatedResult;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    public String[] getRow() {
        return row;
    }

    public void setRow(String[] row) {
        this.row = row;
    }
}
