import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {
    
    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;

        // find the first available block that fits the Process the simply return the address (index).
        for (int i = 0; i < availableBlockSizes.length; i++) {
            if (p.getMemoryRequirements() <= availableBlockSizes[i]) {
                address = i;
                break;
            }
        }
        return address;
    }

}
