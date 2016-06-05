package com.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinRouter;

/**
 * Created by koxa on 05.06.2016.
 */
public class FileProcessorFactory {

    public static final String AKKA_TEST = "AkkaTest";
    public static final String ON_END_LISTENER = "onEndListener";
    public static final String MASTER = "master";

    public static ActorRef createProcessor(final int numberOfWorkers)
    {
        ActorSystem system = ActorSystem.create(AKKA_TEST);

        final ActorRef onEndListener = system.actorOf(Props.create(OnEndListener.class), ON_END_LISTENER);

        return system.actorOf(Props.create(CounterActor.class, onEndListener)
                .withRouter(new RoundRobinRouter(numberOfWorkers)), MASTER);
    }
}
