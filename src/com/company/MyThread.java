package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//public class MyThread extends Thread implements Runnable {
//    String[] fileNames = {};
//    int sum = 0;
//
//    public void MyThread(String[] fileNames){
//        this.fileNames = fileNames;
//    }
//
//    public void run(){
//        this.sum += Ex2_1.getNumOfLines(this.fileNames);
//    }
//
//    public int getSum(){
//        return sum;
//    }
//
//}

public class MyThread extends Thread implements Runnable {
    String fileName = "";
    int end = 0;
    int sum = 0;

    public MyThread(String name, int end){
        this.fileName = name;
        this.end = end;
    }

    public void run(){
        while(convert2int(this.fileName) < this.end){
            helper();
        }
    }

    public void helper(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            while (reader.readLine() != null) {
                this.sum++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        if(convert2int(this.fileName) < this.end){
            this.fileName = "file_"+(convert2int(this.fileName)+1)+".txt";
        }
        else{
            getSum();
        }
    }


    public static int convert2int(String str){
        String t = str.substring(str.indexOf("_")+1);
        String t1 = t.substring(0, t.indexOf("."));
        return Integer.parseInt(t1);
    }


    public int getSum(){
        return sum;
    }

//    public static void main(String[] args) {
//        String d = "file_123.txt";
//        int s = convert2int(d);
//        System.out.println(s);
//    }

}