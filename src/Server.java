import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server extends Thread{
    private final ChannelsManager manager = new ChannelsManager();
    public Semaphore hasConnection = new Semaphore(1);
    private boolean works = true;
    private ServerSocket server = new ServerSocket(28041);
    private final boolean printing;

    public Server(boolean print) throws IOException {
        printing = print;
    }

    public ChannelsManager getManager() {
        return manager;
    }

    public InetAddress getInetAddress() {
        return server.getInetAddress();
    }

    @Override
    public void run() {
        try {
            hasConnection.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (works) {
            try {
                Socket connection = server.accept();
                Channel channel = new Channel(connection, printing);
                manager.addChannel(connection.getInetAddress(), channel);
                channel.start();
                hasConnection.release(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
