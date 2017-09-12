import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DELL on 12.09.2017.
 */
public class Server {
    private final static int PORT = 19000;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started, waiting for connection!");

            Socket socket = serverSocket.accept();
            System.out.println("Accepted. " + socket.getRemoteSocketAddress());

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                int length = in.read();
                byte[] data = new byte[length];
                int readBytes = in.read(data);

                if (length != readBytes) throw new Exception("Some data is corrupted ...");

                String line = new String(data, "UTF-8");
                System.out.printf("Client> %s", line);

                out.write(line.length());
                out.write(line.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
            serverSocket.close();
        }
    }
}
