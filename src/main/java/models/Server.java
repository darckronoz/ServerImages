package models;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static boolean status = true;
    private static final String IMAGES_PATH = "D:\\test"; /*Pasar tambien por la interfaz*/

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (status){
                System.out.println("Server initialized");
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private InputStream in;
        private OutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
                int request = in.read();
                switch (request) {
                    case 1:
                        saveImage();
                        break;
                    case 2:
                        getImages();
                        break;
                    default:
                        out.write(0);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        public void getImages() throws IOException {
            int images = ImageProcessor.getImagesCount(IMAGES_PATH);
            System.out.println(images);
            out.write(images); //Enviar cantidad de imagenes.
            byte[][] imagesList = ImageProcessor.getImagesList(IMAGES_PATH);
            for (int i = 0; i < images; i++) {
                byte[] image = imagesList[i];
                out.write(image);
                out.flush();
            }
        }
        /*lee un array de bytes y lo envia al procesador de imagenes para que lo guarde
        * retorna 1 si se guardó correctamente
        * retorna 0 si hubo un error al guardar*/
        public void saveImage() throws IOException {
            byte[] imageBytes = new byte[229600];
            int image = in.read(imageBytes);
            try{
                if(ImageProcessor.saveImage(IMAGES_PATH, imageBytes)) {
                    out.write(1);
                }else {
                    out.write(0);
                }
            }catch (Exception ex) {
                System.err.println("Error al almacenar la imagen: " + ex.getMessage());
                out.write(0);
            }
        }
    }

}
