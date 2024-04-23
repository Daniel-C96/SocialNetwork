package com.example.SocialNetwork.service.cloudinary;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SocialNetwork.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryStorageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private FileService fileService;

    public String uploadFile(MultipartFile file, String path) throws IOException {
        File fileObj = fileService.convertMultiPartFileToFile(file);
        Map params = Map.of("folder", path);
        Map result = cloudinary.uploader().upload(fileObj, params);
        fileObj.delete();
        String url = (String) result.get("url");
        return url.substring(url.lastIndexOf("/") + 1);
    }

    //IMPLEMENT DELETE METHOD
}

