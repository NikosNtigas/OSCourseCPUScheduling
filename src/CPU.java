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
                if (p.getPCB().getState() == ProcessState.NEW && p.getArrivalTime() <= clock && mmu.loadProcessIntoRAM(p)) { //checking if the process has arrived and it fits in the memory
                    p.getPCB().setState(ProcessState.READY, clock); // the NEW process becomes READY in the scheduler queue
                    scheduler.addProcess(p); // adding each process to the scheduler based on their arrival time
                    currentProcess++;
                }
            }
            
            tick();
            Process p = scheduler.getNextProcess();
            if (p != null) {
                p.run();
                if (p.getPCB().getState() == ProcessState.TERMINATED) {
                    scheduler.removeProcess(p); //remove process from the scheduler when it's terminated
                    for (int[] slot : mmu.getProcessInMemorySlot()) {   //Finding the memory slot and block where the current process is allocated
                        if(p.getPCB().getPid() == slot[0]){
                            ArrayList<MemorySlot> currentMemoryBlock = mmu.getBlockMemorySlots().get(slot[1]); //This list contains all the occupied memory slot within a block
                            mmu.getCurrentlyUsedMemorySlots().remove(currentMemoryBlock.get(slot[2]));  // Removing the memory slot from the currentlyUsedMemorySlots

                            debugging(p, slot[1], currentMemoryBlock.get(slot[2])); // print info of a process
                            currentMemoryBlock.remove(slot[2]); // Removing the used memory slot from the current block


                            for (int i = slot[2]; i <currentMemoryBlock.size(); i++) {  //Shifting the loaded processes to the start of the block in order to avoid fragmentation (compaction)
                                currentMemoryBlock.get(i).setStart(currentMemoryBlock.get(i).getStart() - p.getMemoryRequirements());
                                currentMemoryBlock.get(i).setEnd(currentMemoryBlock.get(i).getEnd() - p.getMemoryRequirements());
                            }
                            mmu.getAvailableBlockSizes()[slot[1]] += p.getMemoryRequirements(); //Increasing the block size by the amount of memory that was allocated to the terminated process
                            mmu.getProcessInMemorySlot().remove(slot);
                            int index = 0;
                            for (int[] s : mmu.getProcessInMemorySlot()) if(s[1] == slot[1]) s[2] = index++; //Re-indexing the references to the memory slots of the current block
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
}
