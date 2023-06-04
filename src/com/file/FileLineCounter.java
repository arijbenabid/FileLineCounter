package com.file;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;


public class FileLineCounter {
    public static void main(String[] args) {
        Counter counter = new Counter();

        List<String> filePaths = new ArrayList<>();
        try (BufferedReader inputReader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = inputReader.readLine()) != null) {
                filePaths.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file.");
            return;
        }

        List<Thread> threads = new ArrayList<>();
        for (String filePath : filePaths) {
            FileLineCounterRunnable runnable = new FileLineCounterRunnable(counter, filePath);
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + thread.getName());
            }
        }

        int totalLines = counter.getCount();
        System.out.println("Total lines: " + totalLines);

        try (FileWriter outputWriter = new FileWriter("output.txt")) {
            for (String filePath : filePaths) {
                outputWriter.write(filePath + ": " + counter.getCount() + " lines\n");
            }
            outputWriter.write("\nTotal lines: " + totalLines);
        } catch (IOException e) {
            System.err.println("Error writing output file.");
        }
    }
}