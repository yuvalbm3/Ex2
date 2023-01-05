# OOP.Assignment2

## Overview:
In this assingment we were asked to create several text files, calculate the total number of lines in these files in three different ways and compare the running times between the methods.

## Threads:
thread is a computer science concept used in operating systems to describe a running context in an address space. The running mechanism using threads allows the user of the operating system to be provided with response speed and continuity of operation when the process performs several tasks at the same time.


## Thread Pool:
In computer programming, a thread pool is a software design pattern for achieving concurrency of execution in a computer program.  a thread pool maintains multiple threads waiting for tasks to be allocated for concurrent execution. By maintaining a pool of threads, the model increases performance and avoids latency.

structrue:

|Class    |Description|
|:---------|:-------------|
|Ex2_1| In this class we will implement the functions for the required calculation of the total number of rows in three different methods.|
|MyThread| This class inherits from the Thread class and implements the Runnable class, when the constructor receives the name of the file and the run function calculates the number of lines of this file..|
|LineCounterTask| This class implements the Callable class, when the constructor receives the name of the file and the call method calculates the number of lines of one file.|

## Ex2_1
+ *createTextFiles:* This method creates n files where the number of lines in each file is a random number, obtained with the help of seed and bound parameters.
+ *helper_f:* This method calculate the number of lines in a giving file.
+ *getNumOfLines:* This method gets an array of files and returns the sum of the lines in all files that are in the array.
+ *getNumOfLinesThreads:* This method gets an array of files returns the sum of the lines in all files that are in the array using threads.
+ *getNumOfLinesThreadPool:* This method gets an array of files returns the sum of the lines in all files that are in the array using thread pool.

## MyThread
+ *run:* The run method will be activated when we activate the start method on the thread and calculate the sum of line in the current file.
+ *getSum:* get the sum of lines of the file.

## LineCounterTask
+ *call:* The call method calculates the sum of the current file and return the sum of lines of a file.

### UML:


## Explanation of the implementation of the functions:
In the createTextFiles method we created n files where the number of their lines is random- we did this with the help of seed,which generates a sequence of numbers in a way that looks random. 
We used the nextInt function of the Random class and defined that the rows will be up to a range of a certain bound which will also be received in a function signature.
This function returns array of files.
Our goal is to perform a count of the files we have created when in the first method we perform a normal count, in the second method we perform a count using threads and in the third method we perform a count using a thread pool.
Finally we would like to compare all the running times of the three methods.
In the first method(getNumOfLines), we performed a normal count using helper_f function, when we looped through all the files in the file array and added up the number of lines in each file to a certain value that we returned.
In the second method(getNumOfLinesThreads), we created an array of MyThread object which this class is a class that we build that inherits from the Thread class and implements Runnable, and when the thread is activated, a calculation of the number of lines in a certain file will be performed.
After that we summed the result of each thread in the array to a value that will be returned at the end of the function run.
In the third method(getNumOfLinesThreadPool) we built a pool of threads the size of the number of files.
We defined a list of tasks, each task received a file that it needs to calculate the number of lines. We have defined a list of futures, which will hold the result of each task. 
Finally we went through the list of results and summed the results to the value that will be returned at the end of the function run.

### Running times of different methods:
![image](https://user-images.githubusercontent.com/93923600/210585012-da9e657b-d846-457b-85e5-bd1ff4008ac9.png)

### conclusions:

### Collaborators
- *Noam David*
- *Yuval Bar-Maoz*
