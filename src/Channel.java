import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Channel extends Thread {
    private final Socket connection;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream incomingMessages;
    private final boolean printing;

    public final Queue<Message> messages = new LinkedList<>();

    public Channel(Socket connection, boolean print) throws IOException {
        this.connection = connection;
        System.out.println("Connected to inetAddress: "+connection.getInetAddress().toString());
        incomingMessages = new ObjectInputStream(connection.getInputStream());
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        printing = print;
    }

    public synchronized Message read() {
        return messages.poll();
    }

    public synchronized boolean hasMessage() {
        return messages.peek() != null;
    }

    public synchronized void send(Message message) throws IOException {
        outputStream.writeObject(message);
    }

    @Override
    public void run() {
        while (!connection.isClosed()) {
            try {
                Message m = (Message)(incomingMessages.readObject());
                messages.add(m);
                if (printing)
                    System.out.println(m.getContent());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
