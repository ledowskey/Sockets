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
            System.out.println("Server started, witing for connection!");

            Socket socket = serverSocket.accept();
            System.out.println("Accepted. " + socket.getInetAddress());

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                byte[] buf = new byte[32 * 1024];
                int readBytes = in.read(buf);

                String line = new String(buf,0,readBytes);
                System.out.printf("Client> %s", line);

                out.write(line.getBytes());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
            serverSocket.close();
        }
    }
}
