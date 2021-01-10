
public class SJF extends Scheduler {
    public SJF() {
    }

    public void addProcess(Process p) {
        processes.add(p);
    }

    public Process getNextProcess() {
        int min_burst_time = Integer.MAX_VALUE;
        int position = 0;
        if (processes.isEmpty())
            return null;

        for (Process p : processes) {
            if (p.getPCB().getState() == ProcessState.RUNNING)
                return p;
        }

        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getBurstTime() < min_burst_time) {
                min_burst_time = processes.get(i).getBurstTime();
                position = i;
            }
        }
        return processes.get(position);
    }

}
