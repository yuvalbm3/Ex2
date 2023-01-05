package com.company;

public class MyThread extends Thread implements Runnable {
    private String fileName;
    int sum=0;
    /**
     * constructor
     * @param fileName is the name of the file.
     */
    public MyThread(String fileName){
        this.fileName=fileName;
    }
    /**
     * The run function will be activated when we activate the start method on the thread.
     * calculate the sum of line in the current file.
     */
    public void run(){
        this.sum+=Ex2_1.helper_f(this.fileName);
    }
    /**
     * get the sum of lines of the file.
     **/
    public int getSum(){
        return sum;
    }

}
