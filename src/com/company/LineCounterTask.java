package com.company;

import java.util.concurrent.Callable;

class LineCounterTask implements Callable<Integer> {
    private String fileName;

    /**
     * constructor
     * @param fileName is the name of the file.
     */
    public LineCounterTask(String fileName) {
        this.fileName = fileName;
    }
    /**
     * call is a function witch return value,
     * Unlike the run function which does not return a value.
     * calculate the sum of the current file.
     * @return the number of lines in file.
     */
    public Integer call(){
        return Ex2_1.helper_f(fileName);
    }
}
