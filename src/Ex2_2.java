import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
        System.out.println("FASBAS");
        TaskType lev = TaskType.COMPUTATIONAL;
        System.out.println(lev);
//        TaskType op = TaskType.COMPUTATIONAL;
//        System.out.println(op.typePriority);
//        System.out.println(op.getPriorityValue());
    }

//    public class Task<T>{
//        int priority = 0;
//        TaskType tt;
//        List<Callable<Object>> tasks = new ArrayList<>();
//
//        public Task(Callable <T>, TaskType tt1){
//            this.tt = tt1;
//            this.priority = pri;
//        }
//
//        public <T> name(){
//
//        }
//
//        public static Task factory(Callable <T>, TaskType tt){
//            if(tt==Null){
//                tt.get
//            }
//            return new Task(<T>, );
//        }


//
//    }
//
//    public class CustomExecutor{
//        int cores = Runtime.getRuntime().availableProcessors();
//    }

}
