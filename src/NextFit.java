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

        //Search from currentPosition until end of Block
        for (int i = currentAddress; i < availableBlockSizes.length; i++) {
            if (p.getMemoryRequirements() <= availableBlockSizes[i]) {
                address = i;
                fit = true;
                break;
            }
        }
        //If previous search failed then search from the beginning of the block until the current Position
        for (int i = 0; i < currentAddress && !fit; i++)
            if (p.getMemoryRequirements() <= availableBlockSizes[i]) {
                address = i;
                break;
            }

        //Update current position after process has been allocated to memory
        if (address != -1) currentAddress = address != availableBlockSizes.length - 1 ? address : 0;
        return address;
    }
}
