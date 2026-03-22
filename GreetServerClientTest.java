import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;

public class GreetServerClientTest {

    private GreetServer server;
    private GreetClient client;

    @BeforeEach
    public void setUp() throws IOException {
        server = new GreetServer();
        new Thread(() -> {
            try {
                server.start(6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        // Wait for the server to start
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
    }

    @AfterEach
    public void tearDown() throws IOException {
        client.stopConnection();
        server.stop();
    }

    @Test
    public void givenGreetingClient_whenServerResponds_thenCorrect() throws IOException {
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @Test
    public void givenUnrecognizedGreetingClient_whenServerResponds_thenCorrect() throws IOException {
        String response = client.sendMessage("hi there");
        assertEquals("unrecognised greeting", response);
    }
}
