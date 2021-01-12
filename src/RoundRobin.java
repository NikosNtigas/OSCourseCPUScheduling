
public class RoundRobin extends Scheduler {

    private int quantum;

    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
    }

    public RoundRobin(int quantum){
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        super.processes.add(p);
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        if (super.processes.isEmpty())
            return null;
        if (super.processes.get(0).getPCB().getState() == ProcessState.RUNNING && super.processes.get(0).getRunTime() % quantum == 0) {
            super.processes.get(0).waitInBackground();
            super.processes.add(super.processes.get(0));
            super.processes.remove(0);
        }
        return super.processes.get(0);
    }
}
