package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String START_SERVER_MESSAGE = "Server iniciado..";
    public static final String CLIENT_CONECTED_MESSAGE = "Cliente conectado desde : ";
    public static final String MENU = "Que desea hacer marque: \\1. Subir archivo \\ 2. Descargar";
    public static final String NO_VALID_OPTION_MESSAGE = "eso no es una opcion suerte";
    private static int PORT = 5555;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private ServerSocket serverSocket = null;
    private Socket sc = null;

    private boolean stateServer;

    public Server() {
        this.stateServer = true;
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println(START_SERVER_MESSAGE);

            while (stateServer){
                sc = serverSocket.accept();
                System.out.println(CLIENT_CONECTED_MESSAGE + sc.getInetAddress());

                this.inputStream = new DataInputStream(sc.getInputStream());
                this.outputStream = new DataOutputStream(sc.getOutputStream());

                outputStream.writeUTF(MENU);

                int valor = Integer.parseInt(inputStream.readUTF());

                switch (valor){
                    case 1:
                        outputStream.writeUTF(Client.SELECT_IMAGE_OPTION);
                        break;
                    case 2:
                        outputStream.writeUTF(Client.DOWNLOAD_MESSAGE_OPTION);
                        break;
                    default:
                        outputStream.writeUTF(NO_VALID_OPTION_MESSAGE);
                        break;
                }

                sc.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}
