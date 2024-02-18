package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private ServerSocket serverSocket = null;
    private Socket sc = null;
    private boolean stateServer;

    public Server(int port) {
        this.stateServer = true;
        try {
            this.serverSocket = new ServerSocket(port);
            while (stateServer){
                sc = serverSocket.accept();
                this.inputStream = new DataInputStream(sc.getInputStream());
                this.outputStream = new DataOutputStream(sc.getOutputStream());

                int valor = Integer.parseInt(inputStream.readUTF());

                switch (valor){
                    case 1:
                        outputStream.writeUTF(Client.SELECT_IMAGE_OPTION);
                        break;
                    case 2:
                        outputStream.writeUTF(Client.DOWNLOAD_MESSAGE_OPTION);
                        break;
                    default:
                        outputStream.writeUTF("Cambiar esto.");
                        break;
                }

                sc.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
