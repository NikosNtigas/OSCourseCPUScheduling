
public class Main2897 {

	public static void main(String[] args) {
		
		final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
				new Process(0, 1, 10),
                new Process(2, 2, 10)
        };
		final int[] availableBlockSizes = {5, 10};
		MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
		MMU mmu = new MMU(availableBlockSizes,algorithm);
		Scheduler scheduler = new FCFS();
		CPU cpu = new CPU(scheduler, mmu , processes);
        cpu.run();
	}

}
