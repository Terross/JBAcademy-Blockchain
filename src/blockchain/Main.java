package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static volatile boolean flag = true;
    private static volatile MessageList messageList = new MessageList();
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Blockchain blockchain = new Blockchain();
        ExecutorService executor = Executors.newFixedThreadPool(5);


        Thread readThread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                messageList.addMessage(Thread.currentThread().getName());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
        });
        Thread readThread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                messageList.addMessage(Thread.currentThread().getName());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {

                }
            }
        });
        readThread1.start();
        readThread2.start();
        for (int i = 0; i < 15; i++) {

            Future futureBlock = executor.submit(new Miner(blockchain, messageList));
            blockchain.addNewBlock((Block) futureBlock.get());
            readThread1.interrupt();
            readThread2.interrupt();
        }
        executor.shutdown();

        System.out.println(blockchain);
        System.exit(0);
//        String fileName = "blockChain.data";
//        try {
//            SerializationUtils.serialize(blockchain, fileName);
////            Blockchain blockchain1 = (Blockchain) SerializationUtils.deserialize(fileName);
////            System.out.println(blockchain1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
