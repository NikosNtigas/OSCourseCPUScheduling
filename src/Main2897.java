
public class Main2897 {

	public static void main(String[] args) {
		
		final Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 123, 10),
        };
		Scheduler scheduler = new RoundRobin(2);
		CPU cpu = new CPU(scheduler, null , processes);
        cpu.run();
	}

}
