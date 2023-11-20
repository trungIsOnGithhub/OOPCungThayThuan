import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
	// Vector for synchronous, thread-safe
    static Vector<ClientHandler> ClientsList = new Vector<>();

    static AtomicInteger numClients = new AtomicInteger(0);

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
    	int portRunning = Utilities.getCurrentlyUnoccupiedPort("localhost");
  
        ServerSocket server = new ServerSocket(portRunning);
        System.out.println("The Server is waiting for the client on port " + portRunning + "...");

        // running infinite loop for getting client request
        while (true) {
            // socket object to receive incoming client requests
            Socket SocServer = server.accept();
            System.out.println("Start Connection");

            System.out.println("The client with port number = " + SocServer.getPort() + " Added to the server");

            // obtaining input and out streams
            System.out.println("Waiting for the ID of the Client");

            ObjectInputStream ServerInput = new ObjectInputStream(SocServer.getInputStream());
            ObjectOutputStream ServerOutput = new ObjectOutputStream(SocServer.getOutputStream());

            //BufferedReader ClientMessage = new BufferedReader(new InputStreamReader(ServerInput));
            String id = ServerInput.readUTF();

            System.out.println("Client ID : " + id);
            ClientHandler ClientsHand = new ClientHandler(SocServer, id, ServerInput, ServerOutput);

            // Create a new Thread with this object.
            Thread thread = new Thread(ClientsHand);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            ClientsList.add(ClientsHand);

            // start the thread.
            thread.start();

            numClients.incrementAndGet();
        }
    }
}

class ClientHandler implements Runnable {
    static Vector<ClientHandler> NEWClientsVec = new Vector<>();

    final ObjectInputStream input;
    final ObjectOutputStream output;

    public String ID;

    private Socket s;


    public ClientHandler(Socket s, String ID, ObjectInputStream input, ObjectOutputStream output) {
        this.ID = ID;
        this.s = s;
        this.input = input;
        this.output = output;
    }

    public void run() {
        try {
            while (true) {
                System.out.println("The Client Sent this Text to share..");

                // receive "share"
                String received = input.readUTF();

                if (received.equals("Share")) {
                    // receive the IDs
                    received = input.readUTF();
                    NEWClientsVec.clear();

                    String[] IDs = received.split("\\-");

                    for (String idString : IDs) {
                        for (ClientHandler handler : Server.ClientsList) {
                            if (idString.equals(handler.ID) && !handler.ID.equals(this.ID)) {
                                NEWClientsVec.add(handler);
                                break;
                            }
                        }
                        // when the the other clients want to edit show this at the parent
                        NEWClientsVec.add(this);
                    }
                }
                
	            received = input.readUTF();
	            for (ClientHandler handler : NEWClientsVec){
	                if (!ID.equals(handler.ID)) {
	                    handler.output.writeUTF(received);
	                    handler.output.flush();
	                }
	            }
             }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            e.getMessage();
        }

    }
}