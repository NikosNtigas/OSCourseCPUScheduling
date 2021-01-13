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
                    Process temp = processes[j];
                    processes[j] = processes[j + 1];
                    processes[j + 1] = temp;
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
                    for (int[] slot : mmu.getProcessInMemorySlot()) {
                        if(p.getPCB().getPid() == slot[0]){
                            ArrayList<MemorySlot> currentMemoryBlock = mmu.getBlockMemorySlots().get(slot[1]);
                            mmu.getCurrentlyUsedMemorySlots().remove(currentMemoryBlock.get(slot[2]));

                            debugging(p, slot[1], currentMemoryBlock.get(slot[2])); // sout info of a process
                            currentMemoryBlock.remove(slot[2]);


                            for (int i = slot[2]; i <currentMemoryBlock.size(); i++) {  //Shifting the loaded processes to the start of the block in order to avoid fragmentation
                                currentMemoryBlock.get(i).setStart(currentMemoryBlock.get(i).getStart() - p.getMemoryRequirements());
                                currentMemoryBlock.get(i).setEnd(currentMemoryBlock.get(i).getEnd() - p.getMemoryRequirements());
                            }
                            mmu.getAvailableBlockSizes()[slot[1]] += p.getMemoryRequirements();
                            mmu.getProcessInMemorySlot().remove(slot);
                            int index = 0;
                            for (int[] s : mmu.getProcessInMemorySlot()) if(s[1] == slot[1]) s[2] = index++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void tick() {
        clock++;
    }

    private void debugging(Process p, int slot, MemorySlot block) {
        System.out.format("\033[38:2:255:255:255mFINISHED\u001B[36m PROCESS " + p.getPCB().getPid() + "\033[0m\n");
        System.out.format("\t└── Completion: \033[38:2:153:255:102m" + clock + "\033[0m\n");
        System.out.format("\t└── Response: \033[38:2:153:255:102m\t" + p.getResponseTime() + "\033[0m\n");
        System.out.format("\t└── TAT: \033[38:2:153:255:102m\t\t" + p.getTurnAroundTime() + "\033[0m\n");
        System.out.format("\t└── Waiting: \033[38:2:153:255:102m\t" + p.getWaitingTime() + "\033[0m\n");
        System.out.format("\t└── Block: \033[38:2:153:255:102m\t\t" + slot + " SLOT: " + block + "\033[0m\n");
    }
    
    private void debugging1(Process p, int slot, MemorySlot block) {
    	System.out.format("FINISHED PROCESS " + p.getPCB().getPid() + "\n");
        System.out.format("Completion: " + clock + "\n");
        System.out.format("Response: " + p.getResponseTime() + "\n");
        System.out.format("TAT: " + p.getTurnAroundTime() + "\n");
        System.out.format("Waiting: " + p.getWaitingTime() + "\n");
        System.out.format("Block: " + slot + " SLOT: " + block+"\n");
    }
}
