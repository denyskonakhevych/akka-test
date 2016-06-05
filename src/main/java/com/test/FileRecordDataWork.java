package com.test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class FileRecordDataWork {

    private ConcurrentMap<String, AtomicInteger> aggregatedResult;
    private String id;
    private Integer value;
    private AtomicInteger counter;

    public FileRecordDataWork() {
    }

    public FileRecordDataWork(ConcurrentMap<String, AtomicInteger> aggregatedResult, String id, Integer value, AtomicInteger counter) {
        this.aggregatedResult = aggregatedResult;
        this.id = id;
        this.value = value;
        this.counter = counter;
    }

    public ConcurrentMap<String, AtomicInteger> getAggregatedResult() {
        return aggregatedResult;
    }

    public void setAggregatedResult(ConcurrentMap<String, AtomicInteger> aggregatedResult) {
        this.aggregatedResult = aggregatedResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
