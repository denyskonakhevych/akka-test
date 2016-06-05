package com.test;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by koxa on 05.06.2016.
 */
public class TestDataGenerator {
    private static final Integer UNIQUE_IDS_COUNT = 1_000;
    private static final Integer TOTAL_ROWS_COUNT = 100_000;
    private static final Integer MAX_VALUE = 1_000;

    public static void main(String[] args) {

        List<String> ids = new ArrayList<>(UNIQUE_IDS_COUNT);
        for (int i = 0; i < UNIQUE_IDS_COUNT; i++) {
            ids.add(UUID.randomUUID().toString());
        }

        List<String[]> data = new ArrayList<>(TOTAL_ROWS_COUNT);
        for (int i = 0; i < TOTAL_ROWS_COUNT; i++) {
            data.add(new String[]{getRandomId(ids), getRandomAmount(MAX_VALUE)});
        }
        final File result = new File("data.csv");
        try (CSVWriter writer = new CSVWriter(new FileWriter(result), ';')) {
            for (String[] entry : data) {
                writer.writeNext(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String getRandomAmount(int maxValue) {
        return "" + new Random().nextInt(maxValue);
    }

    private static String getRandomId(List<String> ids) {
        final int randomIdIndex = new Random().nextInt(ids.size());
        return ids.get(randomIdIndex);
    }
}
