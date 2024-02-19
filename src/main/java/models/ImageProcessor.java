package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageProcessor {
    public static boolean saveImage(String path, byte[] image) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSSS");
        String timeStamp = dateFormat.format(new Date());
        String fileName = path + "/" + timeStamp + ".png";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(image);
            fos.flush();
            System.out.println("Imagen guardada en: " + fileName);
            return true;
        } catch (Exception ex) {
            System.err.println("Error al guardar la imagen: " + ex.getMessage());
            return false;
        }
    }

    public static int getImagesCount(String path) {
        File folder = new File(path);
        File[] images = folder.listFiles();
        if(images != null)
            return images.length;
        return 0;
    }

    public static byte[][] getImagesList(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            return null;
        }

        byte[][] imageBytesArray = new byte[listOfFiles.length][];
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                BufferedImage img = ImageIO.read(file);
                if (img != null) {
                    ImageIO.write(img, "png", outputStream);
                    imageBytesArray[i] = outputStream.toByteArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imageBytesArray;
    }

    private static byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
