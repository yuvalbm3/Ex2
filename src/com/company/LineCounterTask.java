package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class LineCounterTask implements Callable<Integer> {
    private String fileName;

    public LineCounterTask(String fileName) {
        this.fileName = fileName;
    }

    public Integer call() {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lineCount;
    }
}
