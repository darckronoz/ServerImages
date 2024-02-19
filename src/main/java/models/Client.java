package models;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final String IPADDRESS = "localhost";
    private static final String IMAGES_PATH = "D:\\testClient"; /*Pasar tambien por la interfaz*/
    private static final int PORT = 55555;
    public static final String ERROR_MESSAGE = "F";
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private Scanner scanner;
    private ImageSelector imageSelector;

    public Client() {
        try {
            this.imageSelector = new ImageSelector();
            this.scanner = new Scanner(System.in);
            Socket socket = new Socket(IPADDRESS, PORT);
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            int request = scanner.nextByte();
            outputStream.write(request);
            switch (request) {
                case 1:
                    saveImage();
                    break;
                case 2:
                    getImages();
                    break;
                default:
                    System.out.println(ERROR_MESSAGE);
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void getImages() throws IOException {
        int images = inputStream.read(); //Cantidad de imagenes
        System.out.println(images);
        byte[][] imagesList = new byte[images][];
        for (int i = 0; i < images; i++) {
            imagesList[i] = new byte[229600];
            int image = inputStream.read(imagesList[i]);
            ImageProcessor.saveImage(IMAGES_PATH, imagesList[i]);
        }
    }
    private void saveImage() throws IOException {
        byte[] image = imageSelector.selectImage();
        outputStream.write(image);
        outputStream.flush();
        int response = inputStream.read();
        if (response == 1) {
            /*se guardó exitosamente la imagen entonces :D.*/
            System.out.println("Imagen guardada");
        } else {
            /*no se guardó exitosamente la imagen entonces :C.*/
            System.err.println("Imagen no guardada");
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}
