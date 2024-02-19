package models;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ImageSelector {

    public static final String USER_PROPERTY = "user.home";
    public static final String PATH = "C:\\Users\\Andres\\IdeaProjects\\ServerImagenes\\src\\main\\java\\models\\storagee";
    public static final String JPG = "jpg";
    public static final String PATH_DOWNLOAD_IMAGES = "C:\\Users\\Andres\\IdeaProjects\\ServerImagenes\\src\\main\\java\\models\\downloadImages";
    public static final String CORRECT_SAVE_MESSAGEXD = "Imagen guardada correctamente en: ";
    public static final String IMAGE = "imagen";
    public static final String JPEG = ".jpeg";

    public byte[] selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty(USER_PROPERTY)));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                return convertImageToByteArray(ImageIO.read(fileChooser.getSelectedFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public BufferedImage convertBytesToImage(byte[] bytesDeImagen) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytesDeImagen);
            BufferedImage imagen = ImageIO.read(bais);
            bais.close();
            return imagen;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }

    public void downloadImages(BufferedImage imagen, String nombreArchivo) {
        try {
            File carpeta = new File(PATH_DOWNLOAD_IMAGES);
            File archivoSalida = new File(carpeta, nombreArchivo);
            ImageIO.write(imagen, JPG, archivoSalida);

            System.out.println(ImageSelector.CORRECT_SAVE_MESSAGEXD + archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImageSinceFolder(){
        ArrayList<byte[]> bytesImagesArray = new ArrayList<>();
        File folder = new File(PATH);
        File[] images = folder.listFiles();

        for (int i = 0; i < images.length; i++) {
            byte[] bytesImage = new byte[(int) images[i].length()];
            try {
                FileInputStream inputStream = new FileInputStream(images[i]);
                inputStream.read(bytesImage);
                bytesImagesArray.add(bytesImage);
                downloadImages(convertBytesToImage(bytesImage), IMAGE + (i + 1) + JPEG);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
