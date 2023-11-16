package Chat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9445);
            System.out.println("Сервер запущен");
            while (true) {
                Socket clientSocket = serverSocket.accept();// ждет пока к нему подключится кто-то
                System.out.println("Клиент подключился");

                // второй поток с сообщениями
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                            while (true){
                                String message = inputStream.readUTF();
                                outputStream.writeUTF(message.toUpperCase(Locale.ROOT));
                                System.out.println(message);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
