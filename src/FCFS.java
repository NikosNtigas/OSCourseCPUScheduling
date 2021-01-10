
public class FCFS extends Scheduler {

    public FCFS() {}

    public void addProcess(Process p) {
        processes.add(p);
    } // just add the new process to the end of the array

    public Process getNextProcess() {
        return processes.isEmpty() ? null : processes.get(0);
    } // if empty return null else return first process
}
