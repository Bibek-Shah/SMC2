package com.contact.smc.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 4;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File is required")
                    .addConstraintViolation();
            return false;
        }

        System.out.println("file size: " + file.getSize());
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 4 MB")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

