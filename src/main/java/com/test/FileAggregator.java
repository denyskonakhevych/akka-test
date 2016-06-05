package com.test;

import akka.actor.ActorRef;
import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by koxa on 05.06.2016.
 */
public class FileAggregator {

    final ActorRef processor;
    final File inputFile;

    public FileAggregator(final ActorRef processor, final File inputFile) {
        this.processor = processor;
        this.inputFile = inputFile;
    }

    public void aggregate() {
        final int totalNumberOfLines = getTotalNumberOfLines(inputFile) - 1; // minus headers
        final AtomicInteger counter = new AtomicInteger(totalNumberOfLines);
        final ConcurrentMap<String, AtomicInteger> aggregatedResult = new ConcurrentHashMap<>(totalNumberOfLines);
        try (CSVReader csvReader = new CSVReader(new FileReader(inputFile), ';')) {
            String[] row = null;
            while ((row = csvReader.readNext()) != null) {
                if (row.length != 2)
                    continue;
                String id = row[0];
                Integer value = tryParseAmount(row[1]);
                if (value == null)
                    continue;

                processor.tell(new FileRecordDataWork(aggregatedResult, id, value, counter), null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer tryParseAmount(String stringValue) {
        try {
            return new Integer(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int getTotalNumberOfLines(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null)
                lines++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
