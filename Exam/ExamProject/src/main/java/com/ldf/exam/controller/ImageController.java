/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.controller;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ldiez
 */

@RestController
@RequestMapping(path = "/api")
public class ImageController {
    
    @GetMapping(path="/image/{id}", produces=MediaType.IMAGE_JPEG_VALUE)        
    public ResponseEntity <?> getImage() throws Exception {
        File file = new ClassPathResource("img/OIP.jpg").getFile();
        FileInputStream fileInputStream = new FileInputStream(file);
                byte[] targetArray = new byte[fileInputStream.available()];
        fileInputStream.read(targetArray);

      return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getName()+"\"")
                .body( targetArray);        
    }
    
}
