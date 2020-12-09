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
        for (Process p : processes) {
            scheduler.addProcess(p);
        }
        for (Process p = scheduler.getNextProcess(); p != null; p = scheduler.getNextProcess()) {
            p.run();
            if (p.getPCB().getState() == ProcessState.TERMINATED) {
                scheduler.removeProcess(p);
                System.out.println(CPU.clock); // debugging
                continue;   // simultaneously terminates the process and runs the next one,
            }               // therefore in this specific time the clock does not tick
            tick();
        }
    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        clock++;
    }
}
