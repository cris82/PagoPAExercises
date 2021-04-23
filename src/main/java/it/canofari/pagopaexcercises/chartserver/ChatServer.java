package it.canofari.pagopaexcercises.chartserver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;


/**
 * Tests for very simple chat server that listen on TCP port 10000 for clients.
 */
public class ChatServer {
    static ArrayList<AcceptTelnetClient> connectedClients = new ArrayList();
    static int counter = 1;
    private final int port = 10000;
    private ServerSocket socket;

    private static final Logger LOG = LoggerFactory.getLogger(ChatServer.class);

    // send a message to all connected clients except one (who wrote the message)
    static public void sendMessageToAllClients(String message,String clientIdToSkip){
        for(AcceptTelnetClient acceptTelnetClient:connectedClients){
            if(!acceptTelnetClient.getClientId().equals(clientIdToSkip)) {
                acceptTelnetClient.getOutputStream().println(message);
            }
        }
    }

    // start the server
    public void start(){
        try {
            this.socket = new ServerSocket(port);
            LOG.info("Server started");

            while(true) {
                Socket clientSocket = this.socket.accept();
                // for each connected client and id is assigned in order to distinguish it from the others
                String clientId = "Client" + counter++;

                // create a dedicated thread for the new connected client
                new AcceptTelnetClient(clientId, clientSocket);
            }

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}

/**
 * Each instance of this class refers to a connected client.
 */
class AcceptTelnetClient extends Thread
{
    private Socket socket;
    private String clientId;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    private static final Logger LOG = LoggerFactory.getLogger(AcceptTelnetClient.class);

    AcceptTelnetClient(String clientId,Socket socket) throws IOException {
        this.clientId=clientId;
        this.socket=socket;

        //initialize the input and output steam to communicate with the connected client
        this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);
        this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        start();
    }
    public void run(){

        try {
            ChatServer.connectedClients.add(this);
            boolean alive=true;
            while(alive){
                String line = this.inputStream.readLine(); //receive a message from a client
                String message;
                if(Objects.isNull(line)){ // when a client disconnects a null message is sent
                    alive= false;
                }
                else {
                    message = clientId + "> " + line;
                    LOG.debug (message);
                    ChatServer.sendMessageToAllClients(message, this.clientId);
                }


            }
        }catch (IOException e){
            LOG.debug(e.getMessage());
        }
    }

    public PrintWriter getOutputStream(){
        return this.outputStream;
    }

    public String getClientId(){
        return this.clientId;
    }
}
