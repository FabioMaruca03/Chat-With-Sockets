import java.io.Serializable;

/**
 * This class represents a standard message
 */
public class Message<T extends Serializable> implements Serializable {
    private T content = null;

    public Message(T content) {
        this.content = content;
    }

    public void alter(T newContent) {
        content = newContent;
    }

    public T getContent() {
        return content;
    }
}