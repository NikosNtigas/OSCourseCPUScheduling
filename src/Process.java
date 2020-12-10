
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;

    private int runTime = 0; // total time the process run

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }

    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        switch (pcb.getState()) {
            case NEW:
            case READY:
                pcb.setState(ProcessState.RUNNING, CPU.clock);
                return;
            case RUNNING:
                runTime++; // increasing by 1 every CPU.tick() & important for knowing when a process terminates!
                if (runTime == burstTime) pcb.setState(ProcessState.TERMINATED, CPU.clock);
            case TERMINATED:
        }
    }

    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        pcb.setState(ProcessState.READY, CPU.clock);
    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return getTurnAroundTime() - burstTime;
    }

    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return pcb.getStartTimes().get(0) - arrivalTime;
    }

    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return pcb.getStopTimes().get(pcb.getStopTimes().size() - 1) - arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
}
