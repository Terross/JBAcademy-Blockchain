package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block implements Serializable {
    private final long timeStamp = new Date().getTime();
    private int id;
    private String previousBlockHash;
    private List<String> blockData = new ArrayList<>();
    private String blockHash;
    private Long magic;
    private Long generateTime;
    private String minerId;
    private String nChange;

    public Block(String previousBlockHash, int id, List<String> blockData) {
        long start = System.currentTimeMillis();
        this.id = id;
        this.previousBlockHash = previousBlockHash;
        this.blockData.addAll(blockData);
        this.magic = 0l;
        do {
            magic++;
            blockHash = StringUtil.applySha256(timeStamp + previousBlockHash + magic);
        } while (!Blockchain.checkMagicHash(blockHash, Blockchain.nZeros));
        generateTime = System.currentTimeMillis() - start;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public Long getMagic() {
        return magic;
    }

    public Long getGenerateTime() {
        return generateTime;
    }

    public void setMinerId(String minerId) {
        this.minerId = minerId;
    }

    public String getMinerId() {
        return minerId;
    }

    public String getnChange() {
        return nChange;
    }

    public List<String> getBlockData() {
        return blockData;
    }

    public void setnChange(String nChange) {
        this.nChange = nChange;
    }
}
