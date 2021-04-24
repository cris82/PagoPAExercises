package it.canofari.pagopaexcercises.chartserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Tests for very simple chat server that listen on TCP port 10000 for clients.
 */
public class ChatServer {
    private static List<AcceptTelnetClient> connectedClients = new CopyOnWriteArrayList<>();
    static Integer counter = 0;  //number of total clients which have connected to the server
    private final int port = 10000;
    private ServerSocket socket;

    private static final Logger LOG = LoggerFactory.getLogger(ChatServer.class);

    // send a message to all connected clients except one (who wrote the message)
    static void sendMessageToAllClients(String message,String clientIdToSkip){
        for (AcceptTelnetClient acceptTelnetClient : connectedClients) {  //
            if (!acceptTelnetClient.getClientId().equals(clientIdToSkip)) {
                acceptTelnetClient.getOutputStream().println(message);
            }

        }
    }
    static void removeDisconnectedClient(String clientId){

        connectedClients.removeIf(acceptTelnetClient -> acceptTelnetClient.getClientId().equals(clientId));

    }

    // start the server
    public void start(){
        try {
            this.socket = new ServerSocket(port);
            LOG.info("Server started");

            while(true) {
                // for each connected client and id is assigned in order to distinguish it from the others
                String clientId;
                counter++;
                   clientId = "Client" + counter;

                Socket clientSocket = this.socket.accept();

                // create a dedicated thread for the new connected client
                new AcceptTelnetClient(clientId, clientSocket);
            }

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public static List<AcceptTelnetClient> getConnectedClients(){
        return connectedClients;
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
            ChatServer.getConnectedClients().add(this);
            LOG.debug(this.clientId+ " connected");
            boolean alive=true;
            while(alive){
                String line = this.inputStream.readLine(); //receive a message from a client
                String message;
                if(Objects.isNull(line)){ // when a client disconnects a null message is sent
                    message=this.clientId+ " disconnected";
                    alive= false;
                    ChatServer.removeDisconnectedClient(this.clientId);
                    this.socket.close();
                    // close the i/o streams
                    this.inputStream.close();
                    this.outputStream.close();
                    LOG.debug (message);
                }
                else if(!line.trim().isEmpty()){
                    message = clientId + "> " + line;
                    ChatServer.sendMessageToAllClients(message, this.clientId);
                    LOG.debug (message);
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
