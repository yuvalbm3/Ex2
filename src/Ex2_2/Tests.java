package Ex2_2;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;
import Ex2_2.Ex2_2 ;
//import java.util.logging.Logger;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest(){
        Ex2_2.CustomExecutor customExecutor = new Ex2_2.CustomExecutor();
        var task = Ex2_2.Task.createTask(()->{
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
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        Ex2_2.TaskType lev = Ex2_2.TaskType.COMPUTATIONAL;
        System.out.println(lev);

        Callable<String> callable3 = ()-> {
            StringBuilder sb = new StringBuilder("OOP_ARIEL");
            return sb.reverse().toString();
        };
        Callable<String> callable4 = ()-> {
            StringBuilder sb = new StringBuilder("TEST TEST TEST");
            return sb.reverse().toString();
        };
        Callable<Integer> callable5 = ()-> {
            return (int) (10 * Math.pow(5, 5));
        };
        Callable<Integer> callable6 = ()-> {
            return (int) (10 * Math.pow(5, 5));
        };
        Ex2_2.CustomExecutor ce= new Ex2_2.Ex2_2.CustomExecutor();
        Ex2_2.Task<String> t = new Ex2_2.Ex2_2.Task<String>(callable3, Ex2_2.TaskType.IO);
        Ex2_2.Task<String> t1 = new Ex2_2.Ex2_2.Task<String>(callable4, Ex2_2.TaskType.IO);
        Ex2_2.Task<Integer> t2 = new Ex2_2.Ex2_2.Task<Integer>(callable5, Ex2_2.TaskType.COMPUTATIONAL);
//        other
        ce.submit(callable2);
//        computational
        ce.submit(callable1, lev);
//        io
        var ant1 = ce.submit(t1);
//        io
        ce.submit(t);
//        computational
        ce.submit(t2);
//        other
        ce.submit(callable6);
        System.out.println(ce);
        int ansc2, cant2;
        String anst1, anst2;
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(()-> {
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

        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        customExecutor.gracefullyTerminate();
    }
}

