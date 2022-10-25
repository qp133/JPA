package com.example.userbackend.controller;

import com.example.userbackend.dto.UserDto;
import com.example.userbackend.entity.User;
import com.example.userbackend.request.CreateUserRequest;
import com.example.userbackend.request.UpdateAvatarRequest;
import com.example.userbackend.request.UpdatePasswordRequest;
import com.example.userbackend.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@RequestBody CreateUserRequest request, @PathVariable Integer id) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    //Upload ảnh
    @PutMapping("/users/{id}/update-avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)      //Trả về 204
    public void updateAvatar(@PathVariable Integer id, @RequestBody UpdateAvatarRequest request) {
        userService.updateAvatar(id, request);
    }

    //Update password
    @PutMapping("/users/{id}/update-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)      //Trả về 204
    public void updatePassword(@PathVariable Integer id, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
    }

    //Forgot password
    @PostMapping("/users/{id}/forgot-password")
    public String forgotPassword(@PathVariable Integer id) {
        return userService.forgotPassword(id);
    }

    //Upload file
    @PostMapping("/users/{id}/files")
    public String uploadFile(@PathVariable Integer id, @ModelAttribute("file") MultipartFile file) {
        return userService.uploadFile(id, file);
    }

    //Xem file
    @GetMapping(value = "/users/{id}/files/{fileId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] readFile(@PathVariable Integer id, @PathVariable Integer fileId) {
        return userService.readFile(id, fileId);
    }

    //Lấy danh sách file của user (ngày tạo mới nhất lên đầu)
    //  /api/v1/users/1/files/3
    //  /api/v1/users/1/files/2
    //  /api/v1/users/1/files/1
    @GetMapping(value = "/users/{id}/files")
    public List<String> getFiles(@PathVariable Integer id) {
        return userService.getFiles(id);
    }

    //Xóa file
    @DeleteMapping(value = "/users/{id}/files/{fileId}")
    public void getFiles(@PathVariable Integer id, @PathVariable Integer fileId) {
        userService.deleteFile(id, fileId);
    }

}
