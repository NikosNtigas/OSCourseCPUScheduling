
public class SJF extends Scheduler {
    public SJF() {
    }

    public void addProcess(Process p) {
        processes.add(p);
    } // just add the new process int the array

    public Process getNextProcess() {
        if (processes.isEmpty()) return null; // on empty return null

        // Check if a process is Running in the array and return that.
        for (Process p : processes) if (p.getPCB().getState() == ProcessState.RUNNING) return p;

        // if then non of the processes are Running find the process with the shortest burst time.
        int min_burst_time = Integer.MAX_VALUE;
        int position = 0;
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getBurstTime() < min_burst_time) {
                min_burst_time = processes.get(i).getBurstTime();
                position = i;
            }
        }
        return processes.get(position);
    }

}
