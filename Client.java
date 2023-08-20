import java.io.*;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("Sending request to sender");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connetcion done");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
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
                System.out.println("Chat is terminated");
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
                        System.out.println("Chat has been tremenited by server");
                        socket.close();
                        break;
                    }
                    System.out.println("Server:" + " " + msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Chat is terminated");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
