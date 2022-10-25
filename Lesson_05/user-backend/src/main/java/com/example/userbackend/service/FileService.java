package com.example.userbackend.service;

import com.example.userbackend.entity.Image;
import com.example.userbackend.entity.User;
import com.example.userbackend.exception.BadRequestException;
import com.example.userbackend.exception.NotFoundException;
import com.example.userbackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private ImageRepository imageRepository;
    public String uploadFile(User user, MultipartFile file) {
        validate(file);

        try {
            Image image = new Image();
            image.setUploadedAt(LocalDateTime.now());
            image.setData(file.getBytes());
            image.setUser(user);

            imageRepository.save(image);
            return "/api/v1/users/" + user.getId() + "/files/" + image.getId();
        } catch (IOException e) {
            throw new RuntimeException("Loi khi upload file");
        }

    }

    public void validate(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        //Kiểm tra tên file
        if(fileName == null || fileName.equals("")) {
            throw new BadRequestException("Ten file khong hop le");
        }

        //Kiểm tra đuôi file
        //vd: avatar.png hoặc image.jpg -> lấy ra đuôi png, jpg và kiểm tra trong tập hợp
        //Nếu k có -> Báo lỗi
        //Nếu có -> kiểm tra tiếp dung lượng file
        String fileExtension = getFileExtension(fileName);
        if(!checkFileExtension(fileExtension)) {
            throw new BadRequestException("Dinh dang file khong hop le");

        }

        //Kiểm tra dung lượng (<= 2MB)
        if((double)file.getSize() / 1_048_576L > 2) {
            throw new BadRequestException("File khong duoc vuot qua 2MB");
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if(lastIndexOf == -1) {
            return null;
        }
        return fileName.substring(lastIndexOf + 1);
    }

    private Boolean checkFileExtension(String fileExtension) {
        List<String> extensions = Arrays.asList("jpg", "png", "jpeg");
        return extensions.contains(fileExtension.toLowerCase());
    }

    public byte[] readFile(Integer fileId) {
        Image image = imageRepository.findById(fileId).orElseThrow(()-> {
           throw new NotFoundException("Not found image with id = " + fileId);
        });
        return image.getData();
    }

    public List<String> getFiles(Integer id) {
        List<Image> images = imageRepository.findAll();
        return null;
    }
}