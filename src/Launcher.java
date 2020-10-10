import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple shell main.
 * Run with parameter: (-s) to run the server; (-c) to run the client
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            throw new IllegalArgumentException("Invalid argument passed");
        else {
            if (args[0].equals("-s")) {
                Server server = new Server(true);
                server.start();

                System.out.println("Server started on port 28041");
                System.out.println("Waiting for a connection");

                server.hasConnection.acquire(1);

                Channel channel = server.getManager().getChannel(InetAddress.getLocalHost());

                Scanner s = new Scanner(System.in);

                while (true) {
                    Message m = new Message(s.nextLine());
                    channel.send(m);
                }

            } else if(args[0].equals("-c")) {
                Socket connection = new Socket("localhost", 28041);
                ChannelsManager manager = new ChannelsManager();
                Channel channel = new Channel(connection, true);
                manager.addChannel(connection.getInetAddress(), channel);

                channel.start();

                System.out.println("Your current inetAddress: "+connection.getInetAddress().toString());

                Scanner s = new Scanner(System.in);
                while (true) {
                    Message m = new Message(s.nextLine());
                    channel.send(m);
                }
            }
        }
    }
}
