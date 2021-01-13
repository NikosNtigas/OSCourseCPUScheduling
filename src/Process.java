
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;

    private int runTime = 0; // total time the process run
    private int completionTime = 0; // the exact time a process terminates

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
        // this method runs every time the cpu runs the loaded process
        switch (pcb.getState()) {
            case NEW:
            case READY:
                // on the first run set the state RUNNING and increase the run time
                pcb.setState(ProcessState.RUNNING, CPU.clock);
            case RUNNING:
                runTime++; // increasing by 1 every CPU.tick() & important for knowing when a process terminates!
                if (runTime == burstTime) {
                    pcb.setState(ProcessState.TERMINATED, CPU.clock);
                    completionTime = CPU.clock;
                }
                break;
            case TERMINATED:
                // A terminated process must be removed from the scheduler, so if a terminated process try to run throw an unexpected exception.
                throw new java.lang.RuntimeException("Unexpected exception. Process try to run but the Process was already terminated.");
        }
    }

    public void waitInBackground() {
        pcb.setState(ProcessState.READY, CPU.clock);
    }

    public double getWaitingTime() {
        return getTurnAroundTime() - burstTime;
    }

    public double getResponseTime() {
        return pcb.getStartTimes().get(0) - arrivalTime - 1;
    }

    public double getTurnAroundTime() {
        return pcb.getStopTimes().get(pcb.getStopTimes().size() - 1) - arrivalTime;
    }

    public int getArrivalTime() { return arrivalTime; }

    public int getBurstTime() { return burstTime; }

    public int getMemoryRequirements() { return memoryRequirements; }

    public int getRunTime() { return runTime; }

    public int getCompletionTime() { return completionTime; }
}
