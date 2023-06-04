package com.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class FileLineCounterRunnable implements Runnable {
    private Counter counter;
    private String filePath;

    public FileLineCounterRunnable(Counter counter, String filePath) {
        this.counter = counter;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineCount = 0;
            while (reader.readLine() != null) {
                lineCount++;
            }
            synchronized (counter) {
                counter.increment(lineCount);
            }
            System.out.println(filePath + ": " + lineCount + " lines");
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
    }
}

   
