package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    // All the files code should be here
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // File name of current / original file
        String originalFilename = file.getOriginalFilename();

        // Generate a unique file name (Random UUID)
        String randomId= UUID.randomUUID().toString();
        //mat.jgp --> random int is 1234 ---> new file name 1234.jpg
        String fileName=randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));
        String filePath=path+ File.separator+fileName;

        // Check if path exists or create
        File folder=new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }


        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //returning file name
        return fileName;
    }
}
