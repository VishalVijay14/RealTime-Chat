import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;

            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
        }
    }
}
