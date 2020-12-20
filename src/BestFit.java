import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {
    
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        // boolean fit = false;
        int min=Integer.MAX_VALUE;
        int address = -1;
        for (int i = 0; i < availableBlockSizes.length; i++) {
            if (min > availableBlockSizes[i] && p.getMemoryRequirements() <= availableBlockSizes[i] && currentlyUsedMemorySlots.get(i).getStart() == currentlyUsedMemorySlots.get(i).getEnd()) {
                min = availableBlockSizes[i];
                address=i;
            }
        }
        return address;

    }

}


