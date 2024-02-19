package models;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
}
