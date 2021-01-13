
public class RoundRobin extends Scheduler {

    private int quantum;

    public RoundRobin() {
        this.quantum = 1; // default quantum
    }

    public RoundRobin(int quantum){
        this.quantum = quantum;
    }

    public void addProcess(Process p) { super.processes.add(p); } // just add the new process int the array

    public Process getNextProcess() {
        if (super.processes.isEmpty()) return null;
        // if processes is Running and has complete the quantum running time, mark it as Ready and push it to the end of the array.
        if (super.processes.get(0).getPCB().getState() == ProcessState.RUNNING && super.processes.get(0).getRunTime() % quantum == 0) {
            super.processes.get(0).waitInBackground();
            super.processes.add(super.processes.get(0));
            super.processes.remove(0);
        }
        return super.processes.get(0); // always return the first Process in the array
    }
}
