import java.util.ArrayList;

public class ProcessControlBlock {

    private final int pid;
    private ProcessState state;
    // the following two ArrayLists should record when the process starts/stops
    // for statistical purposes
    private ArrayList<Integer> startTimes; // when the process starts running
    private ArrayList<Integer> stopTimes;  // when the process stops running

    private static int pidTotal = 0;

    public ProcessControlBlock() {
        this.state = ProcessState.NEW;
        this.startTimes = new ArrayList<Integer>();
        this.stopTimes = new ArrayList<Integer>();
        this.pid = pidTotal++; // Every time a Process create increment the pidTotal.
    }

    public ProcessState getState() { return this.state; }

    public void setState(ProcessState state, int currentClockTime) {
        if (this.state == ProcessState.NEW) this.state = state;
        else {
            switch (this.state = state) {
                case READY:
                case TERMINATED:
                    // Helpful to determine the turn around time.
                    stopTimes.add(currentClockTime); // When process terminates or marked as ready (RoundRodin) add a time stamp to the stopTimes.
                    break;
                case RUNNING:
                    // Helpful to determine the response time.
                    startTimes.add(currentClockTime); // When process starts running add a time stamp the the startTimes.
                    break;
            }
        }
    }

    public int getPid() {
        return this.pid;
    }

    public ArrayList<Integer> getStartTimes() {
        return startTimes;
    }

    public ArrayList<Integer> getStopTimes() {
        return stopTimes;
    }

}
