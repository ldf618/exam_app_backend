/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ldf.exam.controller;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(path = "/api/jasper")
public class JasperController {

    @Value("${frontend_host}")
    private String host;
   
    
    @GetMapping(path="/report", produces=MediaType.APPLICATION_PDF_VALUE)        
    public ResponseEntity <?> getPdf() throws Exception {
        File file = new ClassPathResource("pdf/EstadisticaCuestionariosTest.pdf").getFile();//ResourceUtils.getFile("classpath:pdf/EstadisticaCuestionariosTest.pdf");
        FileInputStream fileInputStream = new FileInputStream(file);
                byte[] targetArray = new byte[fileInputStream.available()];
        fileInputStream.read(targetArray);

      return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getName()+"\"")
                .header("Content-Security-Policy", "frame-ancestors 'self' "+host)
                //.header("Content-Security-Policy", "frame-src 'self' http://localhost:8080/")
                .body( targetArray);
    }

}
