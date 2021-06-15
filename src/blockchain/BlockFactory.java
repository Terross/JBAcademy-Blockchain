package blockchain;

import java.util.List;

public class BlockFactory {
    public static Block createBlock(String previousBlockHash, int id, List<String> blockData) {
        return new Block(previousBlockHash, id, blockData);
    }
}
