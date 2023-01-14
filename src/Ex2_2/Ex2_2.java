package Ex2_2;

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



    public static void main(String[] args) throws InterruptedException {
//        TaskType lev = TaskType.COMPUTATIONAL;
//        System.out.println(lev);
//        Callable<Double> callable1 = ()-> {
//            return 1000 * Math.pow(1.02, 5);
//        };
//        Callable<String> callable2 = ()-> {
//            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//            return sb.reverse().toString();
//        };
//        Callable<String> callable3 = ()-> {
//            StringBuilder sb = new StringBuilder("OOP_ARIEL");
//            return sb.reverse().toString();
//        };
//        Callable<String> callable4 = ()-> {
//            StringBuilder sb = new StringBuilder("TEST TEST TEST");
//            return sb.reverse().toString();
//        };
//        Callable<Integer> callable5 = ()-> {
//            return (int) (10 * Math.pow(5, 5));
//        };
//        Callable<Integer> callable6 = ()-> {
//            return (int) (10 * Math.pow(5, 5));
//        };
//        CustomExecutor ce= new CustomExecutor();
//        Task<String> t = new Task<>(callable3, TaskType.IO);
//        Task<String> t1 = new Task<>(callable4, TaskType.IO);
//        Task<Integer> t2 = new Task<>(callable5, TaskType.COMPUTATIONAL);
////        other
//        ce.submit(callable2);
////        computational
//        ce.submit(callable1, lev);
////        io
//        var ant1 = ce.submit(t1);
////        io
//        ce.submit(t);
////        computational
//        ce.submit(t2);
////        other
//        ce.submit(callable6);
//        System.out.println(ce);
//        int ansc2, cant2;
//        String anst1, anst2;
//
//        ce.gracefullyTerminate();
    }

    public static class Task<V> extends FutureTask<V> implements Callable<V>, Comparable<Task<V>> {
        private TaskType tt;
        private int priority;
        private Callable<V> tasks;

        /**
         * Private constructor
         * @param tasks - An operation that may return a value - callable.
         * @param tt - TaskType
         */
        private Task(Callable<V> tasks, TaskType tt){
            super(tasks);
            this.tt=tt;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }

        /**
         *  Private constructor
         * @param tasks - An operation that may return a value - callable.
         */
        private Task(Callable<V> tasks){
            super(tasks);
            this.tt= TaskType.OTHER;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }

        //         factory methode
        public static <V> Task<V> createTask(Callable<V> tasks, TaskType tt) {
            return new Task(tasks, tt);
        }

        @Override
        public V call() throws Exception {
            return tasks.call();
        }

        public int getPriorityValue(){
            return this.priority;
        }

        public String toString(){
            return "TaskType"+this.tt+"priority"+this.priority;
        }

        /**
         *
         * @param o - A Task instance for comparing in order to create priority queue
         * @return integer value base on the comparison
         */
        @Override
        public int compareTo(Task<V> o) {
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


    }

    public static class CustomExecutor  {
        int numOfCores = Runtime.getRuntime().availableProcessors();
        int maxP=0;
        ThreadPoolExecutor executor;


        /**
         * Constructor for the CustomExecutor class
         */
        public CustomExecutor() {
            int MAX_threads = numOfCores - 1;
            int MIN_threads = numOfCores / 2;
            executor = new ThreadPoolExecutor(MIN_threads, MAX_threads, 300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
        }

        //Complete the javadoc#####################################################################
        /**
         * The function add task to the priority list
         * @param t - the Task which is going to be added to the priority queue
         * @param <V>
         * @return
         */
        public <V> Task<V> submit(Task<V> t){
            try {
                if (maxP < t.getPriorityValue()) {
                    this.maxP = t.getPriorityValue();
                    System.out.println(this.maxP);
                }
                if(executor.getQueue().size() <= 1){ this.maxP = 0;}
                else {
                    beforeExecute((Task<V>) executor.getQueue().peek());
                }
                executor.execute(t);
                return(t);
            }
            catch(Exception e){
                System.out.println("The CustomExecutor isn't running anymore.");
                e.printStackTrace();
                return null;
            }
        }
        //Complete the javadoc#####################################################################
        /**
         * The function create task from the given parameters and add it to the priority list
         * @param callable
         * @param tt
         * @param <V>
         * @return
         */
        public <V> Future<V> submit(Callable<V> callable, TaskType tt){
            return submit(Task.createTask(callable, tt));
        }


        //Complete the javadoc#####################################################################
        /**
         * The methode create task from the given parameters and add it to the priority list
         * @param callable
         * @param <V>
         * @return
         */
        public <V> Future<V> submit(Callable<V> callable){
            return submit(Task.createTask(callable, TaskType.OTHER));
        }

        //Complete the javadoc#####################################################################
        /**
         * The methode set the maxPriorityValue of the next item in queue before the execute
         * @param t - the Task with the maximum priority
         * @param <V>
         */
        public <V> void beforeExecute(Task<V> t){
            this.maxP = t.getPriorityValue();
        }

        //get the max priority
        public int getCurrentMax(){
            return this.maxP;
        }

        //shutdown the threads
        public void gracefullyTerminate(){
            executor.shutdown();
        }

    }

}