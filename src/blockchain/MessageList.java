package blockchain;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
    private List<String> messages;

    public MessageList() {
        messages = new ArrayList<>();
    }

    public MessageList(ArrayList<String> messages) {
        this.messages = messages;
    }

    public synchronized void addMessage(String message) {
        messages.addAll(List.of(message, message, "gets 100 VC",
                message, message, message,
                message, message, message,
                message, message, message, message));
    }

    public synchronized List<String> getMessages() {
        return messages;
    }

    public synchronized void clearList() {
        messages.clear();
    }
}
