package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MyThread extends Thread implements Runnable {
    String[] fileNames = {};
    int sum = 0;

    public void MyThread(String[] fileNames){
        this.fileNames = fileNames;
    }

    public void run(){
        this.sum += Ex2_1.getNumOfLines(this.fileNames);
    }

    public int getSum(){
        return sum;
    }

}
