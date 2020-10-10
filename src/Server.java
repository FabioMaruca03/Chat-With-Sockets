import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Server side of the app
 */
public class Server implements Logic {

    private final ServerSocket server;
    private final ExecutorService executor;

    public Server() throws IOException {
        server = new ServerSocket(28041);



        executor = Executors.newFixedThreadPool(10);
    }

    @Override
    public <T extends Serializable> T send(Message<T> message) {
        return null;
    }

    @Override
    public <T extends Serializable> Message<T> asyncGet(long timeout) {
        return null;
    }

    @Override
    public void stop() throws IOException {
        server.close();
        executor.shutdownNow();
    }

    @Override
    public void start() {

    }

    private class Engine implements Runnable {

        private final InputStreamReader reader;
        private final OutputStreamWriter writer;
        private final Semaphore key = new Semaphore(1);

        public Engine(Socket connection) throws IOException {
            reader = new InputStreamReader(connection.getInputStream());
            writer = new OutputStreamWriter(connection.getOutputStream());
        }

        private <T extends Serializable> Callable<T> receiveMessage() {
            return (Callable<T>) () -> {

            }
        }

        @Override
        public void run() {

        }
    }

    private class LoginService implements Runnable {
        private final Queue<Engine> engines = new LinkedList<>();

        @Override
        public void run() {
            while (!server.isClosed()) {
                try {
                    Socket connection = server.accept();
                    Engine e = new Engine(connection);
                    engines.add();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
