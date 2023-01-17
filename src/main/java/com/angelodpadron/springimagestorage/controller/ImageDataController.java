package com.angelodpadron.springimagestorage.controller;

import com.angelodpadron.springimagestorage.model.ImageData;
import com.angelodpadron.springimagestorage.service.ImageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("api/v1/image")
@RequiredArgsConstructor
public class ImageDataController {
    private final ImageDataService imageDataService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImage(@RequestParam("image") MultipartFile imageFile) throws IOException {
        imageDataService.uploadImage(imageFile);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) {
        ImageData imageData = imageDataService.getImage(fileName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(imageData.getType()))
                .body(imageData.getData());
    }
}
