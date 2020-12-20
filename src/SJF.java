
public class SJF extends Scheduler {
    public SJF() {
        /* TODO: you _may_ need to add some code here */
    }

    public void addProcess(Process p) {
        processes.add(p);
        /* TODO: you need to add some code here */
    }
    
    public Process getNextProcess() {
        int min_burst_time = Integer.MAX_VALUE;
        int position=0;
       // boolean flag=false;
if (processes.isEmpty())
    return  null;

             /* TODO: you need to add some code here
             *and change the return value */

    for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getBurstTime() < min_burst_time) {
                min_burst_time = processes.get(i).getBurstTime();
                position = i;
            }

    }
        System.out.print(position + ": position\n");
            return processes.get(position);

        }

}
