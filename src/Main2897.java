
public class Main2897 {

	public static void main(String[] args) {
		
		final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
				new Process(0, 5, 10),
                new Process(2, 2, 10),
                new Process(4, 1, 20),
                new Process(6, 3, 10)
        };
		final int[] availableBlockSizes = {15, 40, 10, 20};
		MemoryAllocationAlgorithm algorithm = new NextFit(availableBlockSizes);
		MMU mmu = new MMU(availableBlockSizes,algorithm);
		Scheduler scheduler = new FCFS();
		CPU cpu = new CPU(scheduler, mmu , processes);
        cpu.run();
	}

}
