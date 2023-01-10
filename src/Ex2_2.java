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
        Task t= new Task(callable1,lev);
        System.out.println(t);
        Task t1= new Task(callable2);
        System.out.println(t1);
        CustomExecutor ce= new CustomExecutor();
        ce.submit(t1);
        ce.submit(t);
        System.out.println(ce);

    }

    public static class Task<Object> implements Callable<Object>, Comparable<Task<Object>> {
        TaskType tt;
        int priority;
        Callable<Object> tasks;

        private Task(Callable<Object> tasks,TaskType tt){
            this.tt=tt;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }
        private Task(Callable<Object> tasks){
            this.tt= TaskType.OTHER;
            this.priority= tt.getPriorityValue();
            this.tasks=tasks;
        }


        public Task Factory(Callable<Object> tasks,TaskType tt) {
            return new Task(tasks, tt);
        }

        @Override
        public Object call() throws Exception {
            return tasks.call();
        }

        public int getPriorityValue(){ //********
            return this.priority;
        }
        
        public TaskType getTt(){ //********
            return this.tt.getType();
        }

        public String toString(){
            return "TaskType"+this.tt+"priority"+this.priority;
        }

        @Override
        public int compareTo(Task<Object> o) {
            if(o.getPriorityValue()<this.priority){
                return this.priority;
            }return o.getPriorityValue();
        }
    }

    public static class CustomExecutor{
        private final int MAX_threads = Runtime.getRuntime().availableProcessors()-1;
        private final int MIN_threads = Runtime.getRuntime().availableProcessors()/2;
        private final long idle= 3000L;
        int maxP=0;

        PriorityBlockingQueue<Task> pq;
        Executor executor;

        public CustomExecutor(){
            this.pq= new PriorityBlockingQueue<>();
            executor= Executors.newFixedThreadPool(MAX_threads);
        }

        public void submit(Callable<Object> callable, TaskType tt){
            Task t= new Task(callable,tt);
            this.pq.add(t);
        }
        public void submit(Callable<Object> callable){
            Task t= new Task(callable);
            this.pq.add(t);
        }

//        public int getMaxPri(Task task){ //***********
//            for(Task t:pq){
//                this.maxP= Math.max(task.compareTo(t));
//            }return max;
//        }

        public String toString(){
            return this.pq.toString();
        }
    }

}