package blockchain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Blockchain implements Serializable {
    private static final Long SerialVersionUID = 7l;
    private List<Block> blocks = new ArrayList<>();
    private List<String> newData;
    public static volatile int nZeros = 0;
    public Blockchain() {
    }

    private synchronized void changeNZeros(Block newBlock) {
        if (newBlock.getGenerateTime() < 15) {
            nZeros++;
        } else if (newBlock.getGenerateTime() > 20) {
            nZeros--;
        }
    }

    private Block createNewBlock(int iForThread) {
        MessageList messageList = new MessageList();
        Block newBlock;
        if (iForThread == 0) {
            newBlock = BlockFactory.createBlock("0", iForThread + 1, List.of("no messages"));
        } else {
            newBlock = BlockFactory.createBlock(blocks.get(blocks.size() - 1).getBlockHash(),
                    blocks.size() + 1, newData);
        }
        return newBlock;
    }

    public static boolean checkMagicHash(String hash, int nZeros) {
        for (int i = 0; i < nZeros; i++) {
            if (hash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        for (int i = 0; i < blocks.size(); i++) {
            if (!checkMagicHash(blocks.get(i).getBlockHash(), nZeros)) {
                throw  new IllegalArgumentException("Wrong hash");
            }
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void addNewBlock(Block block) {
        if (blocks.size() == 0) {
            blocks.add(block);
        } else {
            if (block.getPreviousBlockHash().equals(blocks.get(blocks.size() - 1).getBlockHash())) {
                blocks.add(block);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < blocks.size(); i ++) {

            StringBuilder blockData = new StringBuilder();
            if (i != 0) {
                blockData.append("Block data: \n");
            } else {
                blockData.append("Block data: \n");
            }
            for (int j = 0; j < blocks.get(i).getBlockData().size(); j++) {
                    blockData.append(blocks.get(i).getBlockData().get(j) + "\n");
            }
            stringBuilder.append("Block:\n" +
                    "Created by miner # " + blocks.get(i).getMinerId() + "\n" +
                    "miner" + blocks.get(i).getMinerId() + "gets 100 VC \n" +
                    "Id: " + blocks.get(i).getId() + "\n" +
                    "Timestamp: " + blocks.get(i).getTimeStamp() + "\n" +
                    "Magic number: " + blocks.get(i).getMagic() + "\n" +
                    "Hash of the previous block:\n" +
                    blocks.get(i).getPreviousBlockHash() + "\n" +
                    "Hash of the block:\n" +
                    blocks.get(i).getBlockHash() + "\n" +
                    blockData.toString() +
                    "Block was generating for " + blocks.get(i).getGenerateTime() + " seconds" + "\n" +
                    blocks.get(i).getnChange() + "\n\n");
        }
        return stringBuilder.toString();
    }
}
