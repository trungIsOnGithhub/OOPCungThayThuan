import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Utilities {
	static public int getCurrentlyUnoccupiedPortWrapped(String serverAddress)
										throws UnknownHostException, IOException {
		int[] safePortRange = new int[] { 49152, 65535 };
		
		Socket testSocket = new Socket();
		
		InetAddress inetAddress = InetAddress.getByName(serverAddress);  
		
		for (int port = safePortRange[0]; port <= safePortRange[1]; ++port) {
			testSocket.connect( new InetSocketAddress(inetAddress, port) );
			
			if (testSocket.isConnected()) {
				return port;
			}
		}
		
		return -1; // failed to connect any port in given range
	}

	static public int getCurrentlyUnoccupiedPort(String serverAddress) {
		int port = -1;
		try {
			port = getCurrentlyUnoccupiedPortWrapped(serverAddress);
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			return 0;
		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}
		
		return port; // failed to connect any port in given range
	}
}
