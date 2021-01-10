import java.util.ArrayList;

public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;

    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }

    public void run() {
        for (int i = 0; i < processes.length; i++) {
            for (int j = 0; j < processes.length - 1; j++) {
                if (processes[j].getArrivalTime() > processes[j + 1].getArrivalTime()) {
                    Process temp = processes[i];
                    processes[i] = processes[i + 1];
                    processes[i + 1] = temp;
                }
            }
        } // making sure the processes are in oder based on the arrival time

        currentProcess = 0;

        while (currentProcess < processes.length || !scheduler.processes.isEmpty()) {
            for (Process p : processes) {
                if (p.getPCB().getState() == ProcessState.NEW && p.getArrivalTime() <= clock && mmu.loadProcessIntoRAM(p)) {
                    p.getPCB().setState(ProcessState.READY, clock); // the NEW process becomes READY in the scheduler queue
                    scheduler.addProcess(p); // adding each process to the scheduler based on their arrival time
                    currentProcess++;
                }
            }

            tick();
            Process p = scheduler.getNextProcess();
            if (p != null) {
                p.run();
                System.out.println(p.getPCB().getPid() + " " + CPU.clock + " " + p.getRunTime());
                if (p.getPCB().getState() == ProcessState.TERMINATED) {
                    scheduler.removeProcess(p);
                    debugging(p); // sout info of a process
                }
            }
        }
    }

    public void tick() {
        clock++;
    }

    private void debugging(Process p) {
        System.out.format("\033[38:2:255:255:255mFINISHED\u001B[36m PROCESS " + p.getPCB().getPid() + "\033[0m\n");
        System.out.format("\t└── Completion: \033[38:2:153:255:102m" + clock + "\033[0m\n");
        System.out.format("\t└── Response: \033[38:2:153:255:102m\t" + p.getResponseTime() + "\033[0m\n");
        System.out.format("\t└── TAT: \033[38:2:153:255:102m\t\t" + p.getTurnAroundTime() + "\033[0m\n");
        System.out.format("\t└── Waiting: \033[38:2:153:255:102m\t" + p.getWaitingTime() + "\033[0m\n");
        for (int i = 0; i < mmu.getBlockHasProcess().length; i++) {
            if (mmu.getBlockHasProcess()[i] == p.getPCB().getPid()) {
                mmu.getCurrentlyUsedMemorySlots().get(i).setEnd(mmu.getCurrentlyUsedMemorySlots().get(i).getStart());
                System.out.format("\t└── Block: \033[38:2:153:255:102m\t\t" + i + "\033[0m\n");
                break;
            }
        }
    }
}
