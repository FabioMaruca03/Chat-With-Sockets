import java.io.IOException;
import java.io.Serializable;

public interface Logic {
    <T extends Serializable> T send(Message<T> message);
    <T extends Serializable> Message<T> asyncGet(long timeout);

    void stop() throws IOException;
    void start();
}
