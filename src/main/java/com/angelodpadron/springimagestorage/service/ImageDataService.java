package com.angelodpadron.springimagestorage.service;

import com.angelodpadron.springimagestorage.model.ImageData;
import com.angelodpadron.springimagestorage.repository.ImageDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.angelodpadron.springimagestorage.utils.ImageUtils.compressImage;
import static com.angelodpadron.springimagestorage.utils.ImageUtils.decompressImage;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;

    public void uploadImage(MultipartFile imageFile) throws IOException {
        log.info("Image extension: {}", imageFile.getContentType());
        imageDataRepository.save(
                ImageData
                        .builder()
                        .name(imageFile.getOriginalFilename())
                        .type(imageFile.getContentType())
                        .data(compressImage(imageFile.getBytes()))
                        .build()
        );
    }

    public ImageData getImage(String fileName) {
        ImageData imageData = imageDataRepository
                .findByName(fileName)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        byte[] imageContent = decompressImage(imageData.getData());

        return ImageData
                .builder()
                .name(imageData.getName())
                .type(imageData.getType())
                .data(imageContent)
                .build();
    }
}
