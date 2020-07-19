package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public File getFile(Integer id) {
        return fileMapper.getFile(id);
    }

    public int createFile(String name, String contentType, String size, byte[] data, String userName) {
        final User user = userService.getUserByName(userName);
        final File file = new File(null, name, contentType, size, user.getUserId(), data);
        return fileMapper.insert(file);
    }

    public void deleteFile(Integer id) {
        fileMapper.delete(id);
    }

    public List<File> getAllFiles(String userName) {
        final User user = userService.getUserByName(userName);
        if (user != null) {
            return fileMapper.getUserFiles(user.getUserId());
        }
        return new ArrayList<>();
    }

    public boolean fileExist(String filename) {
        return fileMapper.getFileByName(filename) != null;
    }
}
