public class RoundRobin extends Scheduler {

    private int quantum;
    
    private int check_clock;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
        //variable for saving the time of CPU when a process has started running
        //check_clock + CPU.clock is the time the CPU will set the process in ready state if it has not ended
        check_clock = 0;
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
    	super.processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
    	if(super.processes.isEmpty())
    		return null;
    	//check if a process has ended before its assigned time slice
    	if(processTerminated() && CPU.clock<check_clock+quantum) {
    		check_clock = CPU.clock;
    	}
    	//switch processes when the time for each process has ended
    	//Check if a process ended exactly in its assigned time slice
    	if(CPU.clock == check_clock+quantum)
    		if(!processTerminated()) {
    			super.processes.get(0).waitInBackground();
    	   		super.processes.add(super.processes.get(0));
    	   		super.processes.remove(0);
    	   		check_clock = CPU.clock;
    		}
    	return super.processes.get(0);
    }
    
    private boolean processTerminated() {
    	if(super.processes.isEmpty())
    		return false;
    	return super.processes.get(0).getRunTime()+1 == super.processes.get(0).getBurstTime() ? true : false;
    }
    
    private void showDebug() {
    	System.out.format("PLID:%d BT:%d RT:%d IC:%d CPU:%d\n",super.processes.get(0).getPCB().getPid(),super.processes.get(0).getBurstTime(),super.processes.get(0).getRunTime()+1,check_clock,CPU.clock);
    }
}
