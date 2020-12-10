import javax.swing.plaf.IconUIResource;
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

        int arrivals = 0;
        while (arrivals < processes.length || scheduler.getNextProcess() != null) {
            while (arrivals < processes.length && processes[arrivals].getArrivalTime() == clock) {
                scheduler.addProcess(processes[arrivals++]);    //adding each process to the scheduler based on their arrival time
            }
            Process p = scheduler.getNextProcess();
            if (p != null) {
                p.run();
                if (p.getPCB().getState() == ProcessState.TERMINATED) {
                    scheduler.removeProcess(p);
                    System.out.println(CPU.clock); // debugging
                    continue;  // simultaneously end and start the next process
                }
            }
            tick();
        }
    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        clock++;
    }
}
