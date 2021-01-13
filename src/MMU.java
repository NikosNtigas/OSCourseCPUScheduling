import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    private ArrayList<int[]> processInMemorySlot; //This array is used for keeping references for the set of three (process ID, block ID, memory slot ID)
    private int[] blockSizes; //This array contains the original value of the available block sizes array
    /*
        Each element of this array represents a memory block, each of which
        corresponds to an array containing the memory slots of the respected block
     */
    private ArrayList<ArrayList<MemorySlot>> blockMemorySlots;

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */

        if (currentlyUsedMemorySlots.isEmpty()) {   //Initializing the added arrays
            blockMemorySlots = new ArrayList<>();
            for (int i = 0; i < availableBlockSizes.length; i++) {
                blockMemorySlots.add(new ArrayList<>());
            }
            blockSizes = new int[availableBlockSizes.length];
            System.arraycopy(availableBlockSizes, 0, blockSizes, 0, availableBlockSizes.length);    //Copying the contents of the availableBlockSizes to the blockSizes array
            processInMemorySlot = new ArrayList<>();
        }
        int address = algorithm.fitProcess(p, currentlyUsedMemorySlots);    //Checking if a process fits in the memory
        fit = address != -1;

        if (fit) {
            availableBlockSizes[address] -= p.getMemoryRequirements();  //Removing the allocated space from the availableBlockSizes
            if (blockMemorySlots.get(address).isEmpty()) { //Checking if there are any memory slots in use, inside the current block
                int realAddress = 0;
                for (int i = 0; i < address; i++) { //Calculate the real starting address of the current block
                    realAddress += blockSizes[i];
                }
                //add the new memory slot to the block
                blockMemorySlots.get(address).add(new MemorySlot(realAddress, realAddress + p.getMemoryRequirements() - 1, realAddress, realAddress + blockSizes[address] - 1));
            } else {
                //add the new memory slot at the end of the previous one
                MemorySlot ms = blockMemorySlots.get(address).get(blockMemorySlots.get(address).size() - 1);
                blockMemorySlots.get(address).add(new MemorySlot(ms.getEnd() + 1, ms.getEnd() + p.getMemoryRequirements(), ms.getBlockStart(), ms.getBlockEnd()));
            }
            //Adding this new memory slot the currentlyUsedMemorySlots and the processInMemorySlot reference array
            currentlyUsedMemorySlots.add(blockMemorySlots.get(address).get(blockMemorySlots.get(address).size() - 1));
            processInMemorySlot.add(new int[]{p.getPCB().getPid(), address, blockMemorySlots.get(address).size() - 1});
        }

        return fit;
    }

    public ArrayList<int[]> getProcessInMemorySlot() {
        return processInMemorySlot;
    }

    public ArrayList<ArrayList<MemorySlot>> getBlockMemorySlots() {
        return blockMemorySlots;
    }

    public ArrayList<MemorySlot> getCurrentlyUsedMemorySlots() {
        return currentlyUsedMemorySlots;
    }

    public int[] getAvailableBlockSizes() {
        return availableBlockSizes;
    }
}
