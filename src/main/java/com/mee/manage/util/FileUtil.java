package com.mee.manage.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileUtil {


    public static List<String> files2Base64Imgs(MultipartFile[] files) throws IOException {
        if (files == null || files.length <= 0) {
            return null;
        }

        List<String> base64Imgs = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file == null)
                continue;

            base64Imgs.add(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        return base64Imgs;
    }

    public static List<BufferedImage> files2BufferedImg(MultipartFile[] files) throws IOException {
        if (files == null || files.length <= 0) {
            return null;
        }

        List<BufferedImage> bufferedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            InputStream inputStream = file.getInputStream();
            BufferedImage textImage = ImageIO.read(inputStream);
            bufferedImages.add(textImage);
        }
        return bufferedImages;
    }

}
