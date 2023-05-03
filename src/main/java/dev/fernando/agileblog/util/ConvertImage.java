package dev.fernando.agileblog.util;

import dev.fernando.agileblog.models.PostModel;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ConvertImage {

    public static String convertImage(MultipartFile file, int width, int height) throws IOException {
        byte[] imageBytes = file.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bImage = ImageIO.read(bis);

        // Redimensiona a imagem para o tamanho desejado
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bImage, 0, 0, width, height, null);
        g.dispose();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", bos);
        byte[] resizedImageBytes = bos.toByteArray();

        // Converte os bytes da imagem redimensionada em uma string Base64
        String imgBase64 = Base64.getEncoder().encodeToString(resizedImageBytes);

        return imgBase64;
    }
}

