package com.udacity.jwdnd.course1.cloudstorage.services.storage;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.authentication.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public String storeFile(MultipartFile file, Authentication authentication) {
        String errorMessage = null;
        Integer userId = userService.getUser(authentication.getName()).getId();
        if (fileMapper.getFile(file.getOriginalFilename()) == null) {
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
        } else {
            errorMessage = "File with the same name already exists.";
        }

        return errorMessage;
    }

    public File[] allFiles(Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        return fileMapper.getAllFiles(userId);
    }

    public Boolean deleteFile(Integer fileId) {
        Integer rowsChanged = fileMapper.deleteFile(fileId);
        return rowsChanged > 0;
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileFromId(fileId);
    }

}
