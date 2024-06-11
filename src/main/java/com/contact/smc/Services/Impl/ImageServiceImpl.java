package com.contact.smc.Services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.contact.smc.Services.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.contact.smc.Helper.AppConstant.*;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class.getName());

    @Override
    @Async
    public String uploadImage(MultipartFile image) {
        try {
            String fileName = UUID.randomUUID().toString();
            byte[] data = new byte[image.getInputStream().available()];

            int readImage = image.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", fileName

            ));
            logger.info("Image uploaded successfully");
            return this.getUrlFromPublicId(fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(IMAGE_WIDTH)
                                .height(IMAGE_HEIGHT)
                                .crop(IMAGE_CROP))
                .generate(publicId);
    }
}
