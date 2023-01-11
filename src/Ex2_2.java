import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Ex2_2 {

    public enum TaskType {
        COMPUTATIONAL(1){
            @Override
            public String toString(){return "Computational Task";}
        },
        IO(2){
            @Override
            public String toString(){return "IO-Bound Task";}
        },
        OTHER(3){
            @Override
            public String toString(){return "Unknown Task";}
        };
        private int typePriority;
        private TaskType(int priority){
            if(validatePriority(priority)){ typePriority = priority;}
            else {
                throw new IllegalArgumentException("Priority is not an integer");
            }
        }
        public void setPriority(int priority){
            if(validatePriority(priority)) this.typePriority = priority;
            else
                throw new IllegalArgumentException("Priority is not an integer");
        }
        public int getPriorityValue(){
            return typePriority;
        }
        public TaskType getType(){
            return this;
        }
        /**
         * priority is represented by an integer value, ranging from 1 to 10
         * @param priority
         * @return whether the priority is valid or not
         */
        private static boolean validatePriority(int priority){
            if(priority < 1 || priority >10){
                return false;
            }
            return true;
        }
    }



    public static void main(String[] args) {
        TaskType lev = TaskType.COMPUTATIONAL;
        System.out.println(lev);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
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
        CustomExecutor ce= new CustomExecutor();
        Task t = new Task(callable3, TaskType.IO);
        Task t1 = new Task(callable4, TaskType.IO);
        Task t2 = new Task(callable5, TaskType.COMPUTATIONAL);
//        other
        ce.submit(callable2);
//        computational
        ce.submit(callable1, lev);
//        io
        ce.submit(t1);
//        io
        ce.submit(t);
//        computational
        ce.submit(t2);
//        other
        ce.submit(callable6);
        System.out.println(ce);

    }

    public static class Task<Object> implements Callable<Object>, Comparable<Task<Object>>, Runnable {
        private TaskType tt;
        private int priority;
        private Callable<Object> tasks;

        //        private constructor
        private Task(Callable<Object> tasks,TaskType tt){
            this.tt=tt;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }

        //        private constructor
        private Task(Callable<Object> tasks){
            this.tt= TaskType.OTHER;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }

        //         factory methode
        public Task createTask(Callable<Object> tasks, TaskType tt) {
            return new Task(tasks, tt);
        }

        @Override
        public Object call() throws Exception {
            return tasks.call();
        }

        public int getPriorityValue(){
            return this.priority;
        }

        public TaskType getTt(){
            return this.tt.getType();
        }

        public String toString(){
            return "TaskType"+this.tt+"priority"+this.priority;
        }

        @Override
        public int compareTo(Task<Object> o) {
            if(o.getPriorityValue()<this.getPriorityValue()){
                return 1;
            }
            else if(o.getPriorityValue()>this.getPriorityValue()){
                return -1;
            }
            else {
                return 0;
            }
        }

        @Override
        public void run() {
            try {
                this.tasks.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class CustomExecutor{
        private final int MAX_threads = Runtime.getRuntime().availableProcessors()-1;
        private final int MIN_threads = Runtime.getRuntime().availableProcessors()/2;
        private final long idle= 300L;
        private int threads_num = MIN_threads + (int)(Math.random() * ((MAX_threads - MIN_threads) + 1));
        private boolean running = true;
        int maxP=0;

        PriorityBlockingQueue<Task> pq;
        ExecutorService executor;

        //Constructor

        public CustomExecutor() {
            this.pq= new PriorityBlockingQueue<>();
            executor= Executors.newFixedThreadPool(threads_num);
        }

        //Add task to the priority list

        public void submit(Task<?> t){
            if(running) {
                if (maxP > t.getPriorityValue()) {
                    this.maxP = t.getPriorityValue();
                }
                this.pq.add(t);
            }
            else{
                System.out.println("The CustomExecutor isn't running anymore.");
            }
        }

        //Create task from the given parameters and add it to the priority list

        public void submit(Callable<?> callable, TaskType tt){
            Task t= new Task(callable,tt);
            submit(t);
        }

        //Create task from the given parameters and add it to the priority list

        public void submit(Callable<?> callable){
            Task t= new Task(callable);
            submit(t);
        }

        public void gracefullyTerminate() throws InterruptedException {
//            List<Ex2_2.Task<?>> tasks = new ArrayList<Ex2_2.Task<?>>();
            while (!this.pq.isEmpty()) {
                Task t = this.pq.poll();
                beforeExecute(this.pq.peek());
                executor.execute(t);
            }
            if (!running){
                executor.wait();
                executor.shutdown();
            }
        }

        public void beforeExecute(Task<?> t){
            setCurrentMax(t.getPriorityValue());
        }

        //set the max priority

        public void setCurrentMax(int cMax){
            this.maxP = cMax;
        }

        //get the max priority

        public int getCurrentMax(){
            return this.maxP;
        }

        //shutdown the threads

        public void shutdown(){
            this.running = false;
        }

        public String toString(){
//            while (!this.pq.isEmpty()) {
//                System.out.println(this.pq.poll());
//            }
//            return this.pq.toString();
            System.out.println(this.pq);
            return this.pq.toString();
        }
    }

}