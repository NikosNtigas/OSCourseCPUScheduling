public class RoundRobin extends Scheduler {

    private int quantum;
    
    private int check_clock;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
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
    	if(processTerminated() && CPU.clock<check_clock+quantum+1) {
    		check_clock = CPU.clock;
    	}
    	if(CPU.clock == check_clock+quantum+1)
    		if(!processTerminated()) {
    			//super.processes.get(0).waitInBackground();
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
