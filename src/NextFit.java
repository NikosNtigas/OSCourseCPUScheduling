import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {
    
	//Variable that saves the last memory position that has been allocated
	private int currentAddress = 0;
	
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        
        //Search from currentPosition until end of Block
        for( int i = currentAddress ; i<availableBlockSizes.length ; i++) {
        	if (p.getMemoryRequirements() <= availableBlockSizes[i]) {
                address = i;
                fit = true;
                break;
            }
    	}
        //If previous search failed then search from the beggining of the block until the current Position
        if( !fit )
        	for (int i = 0 ; i<currentAddress ; i++)
        		if (p.getMemoryRequirements() <= availableBlockSizes[i] ) {
                    address = i;
                    break;
                }
        
        //Update current position after process has been allocated to memory
        if(address != -1)
        	currentAddress = address != availableBlockSizes.length-1 ? address+1 : 0;
        //System.out.format("Current Adress:%d , Address: %d\n", currentAddress, address);	
        return address;
    }
}
