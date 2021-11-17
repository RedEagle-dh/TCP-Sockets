import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoServer1 {
    private int port;
    private int backlog;

    public EchoServer1(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port, backlog);
            System.out.println("EchoServer1 auf " + serverSocket.getLocalSocketAddress() + " gestartet...");

            process(serverSocket);

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void process(ServerSocket server) throws IOException {
        while (true) {
            SocketAddress socketAddress = null;
            try {
                Socket socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                socketAddress = socket.getRemoteSocketAddress();
                System.out.println("Verbindung zu " + socketAddress + " aufgebaut");
                String pattern = "HH:mm";
                SimpleDateFormat f = new SimpleDateFormat(pattern);
                String date = f.format(new Date());
                //System.out.println(date);
                out.println(date);
                //out.println("Server ist bereit...");
                String input;
                while ((input = in.readLine()) != null) {
                    out.println(input);
                }
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                System.out.println("Verbindung zu " + socketAddress + " abgebaut");
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int backlog = 50;
        if (args.length == 2) {
            backlog = Integer.parseInt(args[1]);
        }
        new EchoServer1(port, backlog).startServer();
    }
}
