package it.canofari.pagopaexcercises.chatserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static final Logger LOG = LoggerFactory.getLogger(ChatClient.class);

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final int port = 10000; //it's final since it is indicated directly in the question n°2
    private final int timeout = 3000; //3 seconds

    public void startConnection(String host) {
        try {
            this.clientSocket = new Socket(host, port);
            this.clientSocket.setSoTimeout(timeout); // I create a timeout  in order to unblock the readLine in case nothing has been received
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

    }

    public void sendMessage(String msg) {
       this.out.println(msg);

    }
    public String receiveMessage() {
        String message=null;
        try {
            message = this.in.readLine();
        } catch (IOException e) {
           LOG.debug(e.getMessage());
        }
        return message;
    }

    public void stopConnection() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
