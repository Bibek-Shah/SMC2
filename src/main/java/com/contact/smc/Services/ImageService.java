package com.contact.smc.Services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile image);

    String getUrlFromPublicId(String publicId);
}
