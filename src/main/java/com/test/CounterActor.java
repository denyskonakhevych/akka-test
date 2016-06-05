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
            final String id = work.getId();
            final Integer value = work.getValue();
            final ConcurrentMap<String, AtomicInteger> aggregatedResult = work.getAggregatedResult();
            checkOrInitId(aggregatedResult, id);
            aggregatedResult.get(id).addAndGet(value);
            if (work.getCounter().decrementAndGet() <= 0)
                onEndListener.tell(new AggregatedData(aggregatedResult), getSelf());
        }
    }

    private static void checkOrInitId(final ConcurrentMap<String, AtomicInteger> aggregatedResult, final String id) {
        if (!aggregatedResult.containsKey(id))
            aggregatedResult.putIfAbsent(id, new AtomicInteger(0));
    }
}
