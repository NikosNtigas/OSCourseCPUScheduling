
public class Main3328 {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */

        final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 10),
                new Process(2, 2, 40),
                new Process(3, 1, 25),
                new Process(4, 3, 30)
        };
        final int[] availableBlockSizes = {40, 80, 10, 20}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new FCFS();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }

}