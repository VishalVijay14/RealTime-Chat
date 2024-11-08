import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            // Start a thread to read messages from the server
            new Thread(new ClientHandler(socket)).start();

            // Send messages to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            userName = scanner.nextLine();
            writer.println(userName + " has joined the chat.");

            String message;
            do {
                message = scanner.nextLine();
                writer.println(userName + ": " + message);
            } while (!message.equalsIgnoreCase("exit"));

            socket.close();
        } catch (IOException ex) {
            System.out.println("Error connecting to the server: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("127.0.0.1", 12345);
        client.execute();
    }
}
