import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {

    public WorstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;

        // find the address of the memory block that leaves the maximum possible fragment.
        int max = Integer.MIN_VALUE;
        int address = -1;
        for (int i = 0; i < availableBlockSizes.length; i++) {
            if (max < availableBlockSizes[i] && p.getMemoryRequirements() <= availableBlockSizes[i]) {
                max = availableBlockSizes[i];
                address = i;
            }
        }
        return address;
    }
}
