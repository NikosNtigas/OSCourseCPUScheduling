
public class Main3317 {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */

        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 2, 5),
                new Process(1, 5, 4),
                new Process(2, 10, 6),

        };
        final int[] availableBlockSizes = {15, 20, 10, 10, 50, 40, 10, 15}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new FCFS();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();

    }

}