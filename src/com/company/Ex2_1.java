package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {
    public static int flag = 1;
    /**
     *
     * @param n - natural number that represent the number of text files that will create
     * @param seed - seed for the random function
     * @param bound - Represent the maximum lines that each file could contain.
     * @return String array with all the new files names.
     */

    public static String[] createTextFiles(int n, int seed, int bound){
        String[] filesArray = new String[n];
        Random gen = new Random(seed);
        for (int i = 0; i < n; i++) { //dhdo
            try {
                String file_name = "file_" + (i+1) + ".txt";
                filesArray[i] = file_name;
                PrintWriter writer = new PrintWriter(file_name, "UTF-8");
                int line_num = gen.nextInt(bound);
                System.out.println(line_num);
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
     *
     * @param fileNames - String array with all the files names.
     * @return the sum of all the lines in the files.
     */
    public static int getNumOfLines(String[] fileNames){
        final long startTime = System.currentTimeMillis();
        int lineCount = 0;
        for (String filename : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                while (reader.readLine() != null) {
                    lineCount++;
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        final long endTime = System.currentTimeMillis();
        if(flag==1){
            System.out.println("Total execution time getNumOfLines: " + (endTime - startTime));
            flag ++;
        }
        return lineCount;
    }

//    not suppose to be static
    public static int getNumOfLinesThreads(String[] fileNames){
        final long startTime = System.currentTimeMillis();
        int len = fileNames.length;
        System.out.println("length: "+len);
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            int start = i*(len/4), end = (i+1)*(len/4);
            if(i==3){
                end = len;
            }
            String[] filesPart = Arrays.copyOfRange(fileNames, start, end);
            MyThread t = new MyThread();
            String name= (i+1)+"";
            t.setName(name);
            t.MyThread(filesPart);
            t.start();
            try {
                t.join();
                sum += t.getSum();
//                System.out.println("Thread name: "+name+", number of lines: "+t.getSum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time getNumOfLinesThreads: " + (endTime - startTime));
        return sum;
    }

    //    not suppose to be static
//    public static int getNumOfLinesThreadPool(String[] fileNames) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
////        for (int i = 0; i < fileNames.length; i++) {
////            MyThread t = new MyThread();
////            String tname = (i+1)+"";
////            t.setName(tname);
////            String[] name = {"file_"+(i+1)+".txt"};
////            t.MyThread(name);
////            t.start();
////            service.execute(t.start());
////        }
//        int sum = 0;
//        for (int i = 0; i < fileNames.length; i++) {
//            int taskNumber = i+1;
//            ThreadPool t = new ThreadPool(taskNumber);
//            threadPool.submit(t);
//            sum+=t.getSum();
//        }
//        System.out.println("Final sum is:"+sum);
//        return sum;
//    }

    public static int getNumOfLinesThreadPool(String[] fileNames) {
        final long startTime = System.currentTimeMillis();
        int lineCount = 0;

        // Create a thread pool with the same number of threads as there are files
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);

        // Create a list to store the results of the tasks
        ArrayList<Future<Integer>> results = new ArrayList<>();

        // Submit a task for each file to the thread pool
        for (String fileName : fileNames) {
            results.add(threadPool.submit(new LineCounterTask(fileName)));
        }

        // Wait for all the tasks to complete and sum the results
        for (Future<Integer> result : results) {
            try {
                lineCount += result.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting result from task: " + e.getMessage());
            }
        }

        // Shutdown the thread pool
        threadPool.shutdown();

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time getNumOfLinesThreadPool: " + (endTime - startTime));
        return lineCount;
    }

    public static void main(String[] args) throws InterruptedException {
        String[] sd = Ex2_1.createTextFiles(500, 12, 300);
//        System.out.println(Arrays.toString(sd));
        System.out.println(getNumOfLines(sd));
        TimeUnit.SECONDS.sleep(5);
        System.out.println(getNumOfLinesThreads(sd));
        TimeUnit.SECONDS.sleep(5);
        System.out.println(getNumOfLinesThreadPool(sd));
    }
}





