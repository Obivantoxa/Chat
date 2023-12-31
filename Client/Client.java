package Chat.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 9445);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            /**
             * ????? ??? ???????? ????????? ???? ????????
             */
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String response = is.readUTF();
                            System.out.println(response);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            while (true) {
                String message = scanner.nextLine();
                dos.writeUTF(message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
