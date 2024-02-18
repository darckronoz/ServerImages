package models;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static boolean status = true;

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (status){
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String mensaje;
                while ((mensaje = input.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                    output.println("Mensaje recibido: " + mensaje);
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDownServer() {
        status = false;
    }
}
