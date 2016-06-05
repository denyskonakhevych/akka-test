package com.test;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class CounterActor extends UntypedActor {

    private final ActorRef onEndListener;

    public CounterActor(final ActorRef onEndListener) {
        this.onEndListener = onEndListener;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FileRecordDataWork) {
            FileRecordDataWork work = (FileRecordDataWork) message;
            aggregateRow(work);
            if (work.getCounter().decrementAndGet() <= 0)
                onEndListener.tell(new AggregatedData(work.getAggregatedResult()), getSelf());
        }
    }

    private void aggregateRow(FileRecordDataWork work) {
        if (work.getRow().length != 2)
            return;
        final String id = work.getRow()[0];
        final Integer value = tryParseAmount(work.getRow()[1]);
        final ConcurrentMap<String, AtomicInteger> aggregatedResult = work.getAggregatedResult();
        checkOrInitId(aggregatedResult, id);
        if (value != null)
            aggregatedResult.get(id).addAndGet(value);
    }

    private static Integer tryParseAmount(String stringValue) {
        try {
            return new Integer(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void checkOrInitId(final ConcurrentMap<String, AtomicInteger> aggregatedResult, final String id) {
        if (!aggregatedResult.containsKey(id))
            aggregatedResult.putIfAbsent(id, new AtomicInteger(0));
    }
}
