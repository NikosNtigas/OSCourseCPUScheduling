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
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */
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
                if(p.getPCB().getState() == ProcessState.NEW && p.getArrivalTime() <= clock && mmu.loadProcessIntoRAM(p)){
                    p.getPCB().setState(ProcessState.READY, clock);     // the NEW process becomes READY in the scheduler queue
                    scheduler.addProcess(p);    //adding each process to the scheduler based on their arrival time
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
                    for (int i = 0; i < mmu.getBlockHasProcess().length; i++) {
                        if (mmu.getBlockHasProcess()[i] == p.getPCB().getPid()){
                            mmu.getCurrentlyUsedMemorySlots().get(i).setEnd(mmu.getCurrentlyUsedMemorySlots().get(i).getStart());
                            System.out.println("Memory Block:" + i);
                            break;
                        }
                    }

                    System.out.format("\u001B[37m FINISHED\u001B[36m PROCESS " + p.getPCB().getPid() + "\u001B[32m: "); // debugging
                    System.out.format("%2d", clock);
                    System.out.format("\u001B[37m response: \u001B[32m");
                    System.out.format("%5.1f", p.getResponseTime());
                    System.out.format("\u001B[37m TAT: \u001B[32m");
                    System.out.format("%5.1f", p.getTurnAroundTime());
                    System.out.format("\u001B[37m waiting: \u001B[32m");
                    System.out.format("%5.1f", p.getWaitingTime());
                    System.out.println("\u001B[37m");
                }
            }
        }
    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        clock++;
    }
}
