package com.company;

public class ThreadPool implements Runnable{
    private String[] fileName;
    int sum=0;

    public ThreadPool(int taskId) {
        String name= "file_"+taskId+".txt";
        this.fileName = new String[]{name};
    }

    public void run() {
        this.sum += Ex2_1.getNumOfLines(this.fileName);
    }

    public int getSum(){
        return sum;
    }
}
