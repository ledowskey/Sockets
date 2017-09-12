import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by DELL on 12.09.2017.
 */
public class Client {
    private final static int PORT = 19000;
    private final static String HOST = "localhost";

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        try {
            socket = new Socket(HOST,PORT);

            try(InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()) {

                String line = "Hello up there!!!!";
                out.write(line.getBytes());
                out.flush();

                byte[] data = new byte[32 * 1024];
                int readBytes = in.read(data);
                String inLine = new String(data,0,readBytes);

                System.out.printf("Server> %s", inLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert socket != null;
            socket.close();
        }
    }
}
