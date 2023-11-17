package Chat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class Server {

    public static void main(String[] args) {
        ArrayList<Socket> sockets = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9445);
            System.out.println("Сервер запущен");
            while (true) {
                Socket clientSocket = serverSocket.accept();// ???? ???? ? ???? ??????????? ???-??
                System.out.println("Клиент подключился");
                sockets.add(clientSocket);
                // ?????? ????? ? ???????????
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                            while (true){
                                //???????? ?????????
                                // ?????????? ?????? ???????? ??? ???????? ???????? ?????????
                                String message = inputStream.readUTF();
                                for (Socket socket1 : sockets) {
                                    DataOutputStream out = new DataOutputStream(socket1.getOutputStream());
                                    out.writeUTF(message);
                                }
                                //outputStream.writeUTF(message);
                                System.out.println( "сообщение от клиента: "+message);
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
