package ClientServerProject;

import java.io.*;
import java.net.*;

public class GreetClient {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        GreetClient client = new GreetClient();
        try {
            // Start the connection to the server running on localhost at port 6666
            client.startConnection("127.0.0.1", 6666);
            // Send a greeting message to the server and print the response
            String response = client.sendMessage("hello server");
            System.out.println("Server response: " + response);
            // Stop the connection
            client.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

