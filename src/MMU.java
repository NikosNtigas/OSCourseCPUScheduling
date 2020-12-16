import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    private int[] blockHasProcess;

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

        if (currentlyUsedMemorySlots.isEmpty()) {

            currentlyUsedMemorySlots.add(new MemorySlot(1, 1, 1, availableBlockSizes[0]));
            for (int i = 1; i < availableBlockSizes.length; i++) {
                int n = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getBlockEnd() + 1;
                currentlyUsedMemorySlots.add(new MemorySlot(n, n , n, n + availableBlockSizes[i]));
            }
            blockHasProcess = new int[availableBlockSizes.length];
            for (int i = 0; i < blockHasProcess.length; i++) {
                blockHasProcess[i] = -1;
            }
        }

        int address = algorithm.fitProcess(p, currentlyUsedMemorySlots);
        fit = address != -1;

        if (fit) {
            blockHasProcess[address] = p.getPCB().getPid();
            currentlyUsedMemorySlots.get(address).setEnd(currentlyUsedMemorySlots.get(address).getStart() + p.getMemoryRequirements());
        }

        return fit;
    }

    public int[] getBlockHasProcess() {
        return blockHasProcess;

    }

    public ArrayList<MemorySlot> getCurrentlyUsedMemorySlots() {
        return currentlyUsedMemorySlots;
    }
}
