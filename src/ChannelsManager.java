import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ChannelsManager {
    private final Map<InetAddress, Channel> mapper = new HashMap<>();

    public synchronized void addChannel(InetAddress address, Channel channel) {
        mapper.putIfAbsent(address, channel);
    }

    public synchronized void delChannel(InetAddress address) {
        mapper.remove(address);
    }

    public Channel getChannel(InetAddress address) {
        return mapper.get(address);
    }
}
