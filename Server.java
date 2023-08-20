import java.io.*;
import java.net.*;

class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777, 0, null);
            System.out.println("Server is ready to make connection");
            System.out.println("Waiting.....");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWriting() {
        System.out.println("writer started");
        Runnable r1 = () -> {
            try {
                while (!socket.isClosed()) {
                    BufferedReader b2 = new BufferedReader(
                            new InputStreamReader(System.in));
                    String content = b2.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
                
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is terminated");
            }
        };
        new Thread(r1).start();
    }

    private void startReading() {
        Runnable r2 = () -> {
            try {
                while (!socket.isClosed()) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Chat has been tremenited");
                        // server.close();
                        socket.close();
                        break;
                    }
                    System.out.println("Client:" + " " + msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is terminated");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is server.... going to start server");
        new Server();
    }
}