package com.test;

import akka.actor.ActorRef;

import java.io.File;
import java.io.IOException;

/**
 * Created by koxa on 05.06.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final File data = new File(Main.class.getClassLoader().getResource("data.csv").getFile());

        final ActorRef processor = FileProcessorFactory.createProcessor(1);

        new FileAggregator(processor, data).aggregate();
    }
}
