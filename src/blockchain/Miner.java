package blockchain;

import java.util.List;
import java.util.concurrent.Callable;

public class Miner implements Callable {

    private volatile Blockchain blockchain;
    private volatile MessageList messageList;
    public Miner(Blockchain blockchain, MessageList messageList) {
        this.blockchain = blockchain;
        this.messageList = messageList;
    }

    @Override
    public Block call() throws Exception {
        List<Block> blocks = blockchain.getBlocks();
        Block newBlock = null;
        if (blocks.size() == 0) {
            newBlock = BlockFactory.createBlock("0", 1, messageList.getMessages());
        } else {
            newBlock = BlockFactory.createBlock(blocks.get(blocks.size() - 1).getBlockHash(),
                    blocks.size() + 1, messageList.getMessages());
            messageList.clearList();
        }

        newBlock.setMinerId(Long.toString(Thread.currentThread().getId()));

        if (newBlock.getGenerateTime() < 20) {
            Blockchain.nZeros++;
            newBlock.setnChange("N was increased to " + Blockchain.nZeros);
        } else if (newBlock.getGenerateTime() > 40) {
            Blockchain.nZeros--;
            newBlock.setnChange("N was decreased by 1");
        } else {
            newBlock.setnChange("N stays the same");
        }
        return newBlock;
    }
}
