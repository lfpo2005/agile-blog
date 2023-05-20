package dev.fernando.agileblog.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class ConvertImage {
    public static String convertImagePostBody(MultipartFile file, int width, int height, String destinationFolder) throws IOException {
        byte[] imageBytes = file.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bImage = ImageIO.read(bis);

        // Realizar redimensionamento da imagem, se necessário
        if (bImage.getWidth() > width || bImage.getHeight() > height) {
            Image resizedImage = bImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);
            bImage = bufferedResizedImage;
        }

        // Salvar o arquivo de imagem no sistema de arquivos
        String filename = UUID.randomUUID().toString() + ".jpg";
        String imagePath = destinationFolder + File.separator + filename;
        File outputFile = new File(imagePath);
        ImageIO.write(bImage, "jpg", outputFile);

        // Retornar o caminho ou URL do arquivo de imagem
        return imagePath;
    }

    public static String convertImage(MultipartFile file, int width, int height) throws IOException {
        byte[] imageBytes = file.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bImage = ImageIO.read(bis);

        // Realizar redimensionamento da imagem, se necessário
        if (bImage.getWidth() > width || bImage.getHeight() > height) {
            Image resizedImage = bImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);
            bImage = bufferedResizedImage;
        }

        // Converter a imagem para byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", baos);
        byte[] imageData = baos.toByteArray();

        return Base64.getEncoder().encodeToString(imageData);
    }
}


