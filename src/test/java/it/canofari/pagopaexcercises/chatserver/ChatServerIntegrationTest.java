package it.canofari.pagopaexcercises.chatserver;

import it.canofari.pagopaexcercises.chartserver.ChatServer;
import org.junit.*;
import java.util.concurrent.Executors;
import static org.junit.Assert.assertEquals;

/**
 * Tests for a telnet chat server
 */
public class ChatServerIntegrationTest {


    private ChatClient chatClient1;
    private ChatClient chatClient2;
    private ChatClient chatClient3;



    @BeforeClass
    public static void startServerTest() throws InterruptedException {
        Executors.newSingleThreadExecutor()
                .submit(() -> new ChatServer().start());
        Thread.sleep(500);
    }

    @Before
    public void init(){
        chatClient3 = new ChatClient();
        chatClient3.startConnection("127.0.0.1");
        chatClient2 = new ChatClient();
        chatClient2.startConnection("127.0.0.1");
        chatClient1 = new ChatClient();
        chatClient1.startConnection("127.0.0.1");
    }

    @After
    public void stop() throws InterruptedException {
        chatClient1.stopConnection();
        chatClient2.stopConnection();
        chatClient3.stopConnection();
    }


    @Test
    public void broadcastMessageTest(){
        String message="Ciao";
        chatClient1.sendMessage(message);
        String messageReceivedByClient1=chatClient1.receiveMessage();
        String messageReceivedByClient2=chatClient2.receiveMessage();
        String messageReceivedByClient3=chatClient3.receiveMessage();
        assertEquals(null,messageReceivedByClient1);
        assertEquals(messageReceivedByClient2,messageReceivedByClient3);
        assertEquals(message,messageReceivedByClient2.substring(messageReceivedByClient2.length()-message.length())); //I remove from the received message the clientId
        assertEquals(message,messageReceivedByClient3.substring(messageReceivedByClient3.length()-message.length())); //I remove from the received message the clientId


    }
    @Test
    public void IgnoreEmptyMessageTest(){
        String message=" ";
        chatClient1.sendMessage(message);
        String messageReceivedByClient1=chatClient1.receiveMessage();
        String messageReceivedByClient2=chatClient2.receiveMessage();
        String messageReceivedByClient3=chatClient3.receiveMessage();
        assertEquals(null,messageReceivedByClient1);
        assertEquals(null,messageReceivedByClient2);
        assertEquals(null,messageReceivedByClient3);

    }

    @Test
    public void clientDisconnectionMessageTest1(){
        String message="Ciao";
        chatClient2.stopConnection();
        chatClient1.sendMessage(message);
        String messageReceivedByClient1=chatClient1.receiveMessage();
        String messageReceivedByClient2=chatClient2.receiveMessage();
        String messageReceivedByClient3=chatClient3.receiveMessage();
        assertEquals(null,messageReceivedByClient1);
        assertEquals(null,messageReceivedByClient2);
        assertEquals(message,messageReceivedByClient3.substring(messageReceivedByClient3.length()-message.length())); //I remove from the received message the clientId

    }
    @Test
    public void clientDisconnectMessageTest2(){
        String message="Ciao";
        chatClient3.stopConnection();
        chatClient1.sendMessage(message);
        String messageReceivedByClient1=chatClient1.receiveMessage();
        String messageReceivedByClient2=chatClient2.receiveMessage();
        String messageReceivedByClient3=chatClient3.receiveMessage();
        assertEquals(null,messageReceivedByClient1);
        assertEquals(message,messageReceivedByClient2.substring(messageReceivedByClient2.length()-message.length())); //I remove from the received message the clientId
        assertEquals(null,messageReceivedByClient3);

    }
}
