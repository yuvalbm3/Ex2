package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {
    /**
     * @param n - natural number that represent the number of text files that will create
     * @param seed - seed for the random function
     * @param bound - Represent the maximum lines that each file could contain.
     * @return String array with all the new files names.
     */
    public static String[] createTextFiles(int n, int seed, int bound){
        String[] filesArray = new String[n];
        Random gen = new Random(seed);
        for (int i = 0; i < n; i++) {
            try {
                String file_name = "file_" + (i+1) + ".txt";
                filesArray[i] = file_name;
                PrintWriter writer = new PrintWriter(file_name, "UTF-8");
                int line_num = gen.nextInt(bound);
                for (int j = 0; j < line_num; j++) {
                    writer.println("This isn't my first rodeo.");
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return filesArray;
    }

    /**
     * This method calculate the number of lines in a giving file.
     * @param fileName is the name of the file.
     * @return the sum of the lines in the giving file.
     */
    public static int helper_f(String fileName){
        int lines=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }return lines;
    }

    /**
     * This method returns the sum of the lines in all files that are in the fileNames array.
     * @param fileNames - String array with all the files names.
     * @return the sum of all the lines in the files.
     */
    public static int getNumOfLines(String[] fileNames){
        final long startTime = System.currentTimeMillis();
        int lineCount = 0;
        for (String filename : fileNames) {
            lineCount+= helper_f(filename);
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time getNumOfLines for "+fileNames.length+ " files : " + (endTime - startTime));
        return lineCount;
    }
    /**
     *This method returns the sum of the lines in all files that are in the fileNames array using threads.
     * @param fileNames array that contains files.
     * @return the sum of all the lines in the files.
     */
    public static int getNumOfLinesThreads(String[] fileNames){
        final long startTime = System.currentTimeMillis();
        int len = fileNames.length;
        int sum = 0;
        MyThread[] t_list = new MyThread[len];
        for (int i=0;i<len;i++) {
            MyThread t = new MyThread(fileNames[i]);
            t_list[i]=t;
            String name = (i + 1) + "";
            t.setName(name);
            t.start();
        }
        try {
            for (int j=0; j<len; j++) {
                t_list[j].join();
                sum += t_list[j].getSum();
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time getNumOfLinesThreads for "+fileNames.length+ " files : " + (endTime - startTime));
        return sum;
    }

    /**
     * This method returns the sum of the lines in all files that are in the fileNames array using thread pool.
     * @param fileNames array that contains files.
     * @return the sum of all the lines in the files.
     */
    public static int getNumOfLinesThreadPool(String[] fileNames) {
        final long startTime = System.currentTimeMillis();
        int lineCount = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length); // Create a thread pool with the same number of threads as there are files
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (String fileName : fileNames) {
            tasks.add(new LineCounterTask(fileName));
        }
        List<Future<Integer>> results = new ArrayList<>();// Create a list to store the results of the tasks
        try {
            results = threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future<Integer> result : results) {  // Wait for all the tasks to complete and sum the results
            try {
                lineCount += result.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting result from task: " + e.getMessage());
            }
        }
        threadPool.shutdown();   // Shutdown the thread pool
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time getNumOfLinesThreadPool for "+fileNames.length+ " files : " + (endTime - startTime));
        return lineCount;
    }

//    public static void main(String[] args) throws InterruptedException {
//        String[] sd = Ex2_1.createTextFiles(500, 12, 300);
//        System.out.println(getNumOfLines(sd));
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(getNumOfLinesThreads(sd));
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println(getNumOfLinesThreadPool(sd));
//        System.out.println("----------------------------");
//        TimeUnit.SECONDS.sleep(1);
//        String[] sd1 = Ex2_1.createTextFiles(1000, 12, 300);
//        System.out.println(getNumOfLines(sd1));
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(getNumOfLinesThreads(sd1));
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println(getNumOfLinesThreadPool(sd1));
//        System.out.println("----------------------------");
//        TimeUnit.SECONDS.sleep(1);
//        String[] sd2 = Ex2_1.createTextFiles(2000, 12, 1000);
//        System.out.println(getNumOfLines(sd2));
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(getNumOfLinesThreads(sd2));
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println(getNumOfLinesThreadPool(sd2));
//    }
}