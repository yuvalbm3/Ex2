package Ex2_2;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest() {
        Ex2_2.CustomExecutor customExecutor = new Ex2_2.CustomExecutor();
        var task = Ex2_2.Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, Ex2_2.TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);

        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        Callable<String> callable3 = () -> {
            StringBuilder sb = new StringBuilder("OOP_ARIEL");
            return sb.reverse().toString();
        };
        Callable<Integer> callable4 = () -> {
            return (int) (10 * Math.pow(5, 5));
        };

        var calculateTask = customExecutor.submit(() -> {
            return (int) 10 * Math.pow(5, 5);
        }, Ex2_2.TaskType.COMPUTATIONAL);

        var reverseTask2 = customExecutor.submit(callable3, Ex2_2.TaskType.IO);

        final Double calculate;
        final String reverse;
        try {
            calculate = calculateTask.get();
            reverse = reverseTask2.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reverse);
        logger.info(() -> String.valueOf("Total Price = " + calculate));

        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, Ex2_2.TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, Ex2_2.TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }
    @Test
    public void Task() throws ExecutionException, InterruptedException {

        Ex2_2.CustomExecutor ce = new Ex2_2.CustomExecutor();

        var task_1 = Ex2_2.Task.createTask(()->{  //Make an invalid task (divided by zero).
            return 6/0;
        });
        var exception  = ce.submit(task_1); //The CustomExecutor get invalid task and catch the exception.
        // Use the Factory method with a TaskType.
        var task_2 = Ex2_2.Task.createTask(()->{
                    String a = "bala";
                    String b = " & lala";
                    return a+b;}
                , Ex2_2.TaskType.OTHER);

        var string_1  = ce.submit(task_2);
        assertEquals("bala & lala", string_1.get());

        // Use the Factory method without a TaskType.
        var task_3 = Ex2_2.Task.createTask(()->{
            String a = "yuval";
            return a;});
        assertEquals(Ex2_2.TaskType.OTHER, task_3.getTaskType());//Check if that the default is OTHER = 3.
        assertEquals(task_2.getTaskType(), task_3.getTaskType());

        //Use the Factory method with a TaskType.
        var task_4 = Ex2_2.Task.createTask(()->{
            String a = "noam";
            return a;}, Ex2_2.TaskType.COMPUTATIONAL);
        //Check that the default task type is set correctly and the compare method
        assertEquals(-1, task_4.compareTo(task_3));

        //Change the priority value
        Ex2_2.TaskType.IO.setPriority(5);

        var task_5 = Ex2_2.Task.createTask(()->{
            String a = "TEST";
            return a;}, Ex2_2.TaskType.IO);

        //Check if the priority value have been changed
        assertEquals(1, task_5.compareTo(task_3));
    }
    @Test
    public void CustomExecutor(){
        Ex2_2.CustomExecutor ce = new Ex2_2.CustomExecutor();

        ce.submit(()->{
            int a = 208647701;
            int b = 208573139;
            return a+b;
        });
        ce.submit(()->{
                    String a = "bla";
                    String b = " & bla";
                    return a+b;}
                , Ex2_2.TaskType.IO);

        // The threads done running so the max priority is 0.
        assertEquals(0, ce.getCurrentMax());
    }
}
