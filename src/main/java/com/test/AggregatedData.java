package com.test;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class AggregatedData {
    private ConcurrentMap<String, AtomicInteger> aggregatedResult;

    public AggregatedData() {
    }

    public AggregatedData(ConcurrentMap<String, AtomicInteger> aggregatedResult) {
        this.aggregatedResult = aggregatedResult;
    }

    public ConcurrentMap<String, AtomicInteger> getAggregatedResult() {
        return aggregatedResult;
    }

    public void setAggregatedResult(ConcurrentMap<String, AtomicInteger> aggregatedResult) {
        this.aggregatedResult = aggregatedResult;
    }
}
