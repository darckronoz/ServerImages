package models;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String IPADDRES = "localhost";

    private static final int PORT = 5555;
    public static final String SELECT_IMAGE_OPTION = "Elija una imagen rey";
    public static final String NAME_FOR_FILE_MESSAGE = "Nombre para el archivo";
    public static final String JPEG = ".jpeg";
    public static final String DOWNLOAD_MESSAGE_OPTION = "Descargue esa vuelta";
    public static final String ERROR_MESSAGE = "F";

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private Scanner scanner;

    private Socket socket;

    private ImageSelector imageSelector;

    public Client() {
        try {
            this.imageSelector = new ImageSelector();
            this.scanner = new Scanner(System.in);
            this.socket = new Socket(IPADDRES,PORT);
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

            String message = inputStream.readUTF();
            System.out.println(message);

            outputStream.writeUTF(scanner.nextLine());

            String messageOptionn = inputStream.readUTF();
            if (messageOptionn.equals(SELECT_IMAGE_OPTION)){
                imageSelector.saveImage(JOptionPane.showInputDialog(NAME_FOR_FILE_MESSAGE) + JPEG);
            } else if (messageOptionn.equals(DOWNLOAD_MESSAGE_OPTION)) {
                imageSelector.getImageSinceFolder();
            }else {
                System.out.println(ERROR_MESSAGE);
            }

            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new Client();
    }
}
