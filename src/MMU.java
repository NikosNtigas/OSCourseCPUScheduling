import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    private ArrayList<int[]> processInMemorySlot;
    private int[] blockSizes;
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

        if (currentlyUsedMemorySlots.isEmpty()) {
            blockMemorySlots = new ArrayList<>();
            for (int i = 0; i < availableBlockSizes.length; i++) {
                blockMemorySlots.add(new ArrayList<>());
            }
            blockSizes = new int[availableBlockSizes.length];
            System.arraycopy(availableBlockSizes, 0, blockSizes, 0, availableBlockSizes.length);

//            blockMemorySlots.get(0).add(new MemorySlot(0, -1, 0, availableBlockSizes[0] - 1));
//            for (int i = 1; i < blockMemorySlots.size(); i++) {
//                int n = blockMemorySlots.get(i - 1).get(0).getBlockEnd() + 1;
//                blockMemorySlots.get(i).add(new MemorySlot(n, n, n, n + availableBlockSizes[i] - 1));
//            }
            processInMemorySlot = new ArrayList<>();
        }
        int address = algorithm.fitProcess(p, currentlyUsedMemorySlots);
        fit = address != -1;

        if (fit) {
            availableBlockSizes[address] -= p.getMemoryRequirements();
            if (blockMemorySlots.get(address).isEmpty()) {
                int realAddress = 0;
                for (int i = 0; i < address; i++) {
                    realAddress += blockSizes[i];
                }
                blockMemorySlots.get(address).add(new MemorySlot(realAddress, realAddress + p.getMemoryRequirements() - 1, realAddress, realAddress + blockSizes[address] - 1));
            } else {
                MemorySlot ms = blockMemorySlots.get(address).get(blockMemorySlots.get(address).size() - 1);
                blockMemorySlots.get(address).add(new MemorySlot(ms.getEnd() + 1, ms.getEnd() + p.getMemoryRequirements(), ms.getBlockStart(), ms.getBlockEnd()));
            }
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
