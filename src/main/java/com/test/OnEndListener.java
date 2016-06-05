package com.test;

import akka.actor.UntypedActor;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class OnEndListener extends UntypedActor {
    public void onReceive(Object message) {
        if (message instanceof AggregatedData) {
            AggregatedData fileEnd = (AggregatedData) message;

//            printData(fileEnd);
            dataToFile(fileEnd);

            getContext().system().shutdown();
        } else {
            unhandled(message);
        }
    }

    private void dataToFile(AggregatedData fileEnd) {
        final File result = new File("result.csv");
        try (CSVWriter writer = new CSVWriter(new FileWriter(result), ';')) {
            String[] row = new String[2];
            for (Map.Entry<String, AtomicInteger> entry : fileEnd.getAggregatedResult().entrySet()) {
                row[0] = entry.getKey();
                row[1] = "" + entry.getValue();
                writer.writeNext(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printData(AggregatedData fileEnd) {
        for (Map.Entry<String, AtomicInteger> entry : fileEnd.getAggregatedResult().entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}
