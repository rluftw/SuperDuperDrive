package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public final class StorageService {
    private final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private FileMapper fileMapper;
    private UserService userService;

    public StorageService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public String store(MultipartFile file, Authentication authentication) {
        String errorMessage = null;
        Integer userId = userService.getUser(authentication.getName()).getId();
        try {
            fileMapper.addFile(new File(
                            null,
                            file.getOriginalFilename(),
                            file.getContentType(),
                            Long.toString(file.getSize()),
                            userId,
                            file.getBytes()
                    )
            );
        } catch (IOException e) {
            errorMessage = e.getMessage();
            logger.error(e.getMessage());
        }
        return errorMessage;
    }

    public File[] allFiles(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        return fileMapper.getAllFiles(userId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
