package com.example.userbackend.service;

import com.example.userbackend.dto.UserDto;
import com.example.userbackend.entity.User;
import com.example.userbackend.exception.BadRequestException;
import com.example.userbackend.exception.NotFoundException;
import com.example.userbackend.repository.UserRepository;
import com.example.userbackend.request.CreateUserRequest;
import com.example.userbackend.request.UpdateAvatarRequest;
import com.example.userbackend.request.UpdatePasswordRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private FileService fileService;

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDto.class);
        }
        throw new NotFoundException("Not found user with id = " + id);
    }

    public UserDto createUser(CreateUserRequest request) {
        System.out.println("email = " + request.getEmail());
        if(request.getEmail().isEmpty()) {
            throw new BadRequestException("Email is not blank");
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()) {
            throw new BadRequestException("email = " + request.getEmail() + " already exist");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public UserDto updateUser(Integer id, CreateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        userRepository.delete(user);
    }

    public void updateAvatar(Integer id, UpdateAvatarRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        //Cách 1:
//        user.setAvatar(request.getAvatar());
//        userRepository.save(user);

        //Cách 2:
        userRepository.updateAvatar(id, request.getAvatar());
    }

    public void updatePassword(Integer id, UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        //Kiểm tra xem password cũ có chính xác hay không
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new BadRequestException("Old password is not correct");
        }

        //Kiểm tra xem pass mới có trùng với pass cũ hay không
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("Old password and new password are not the same");
        }

        //Nếu vượt qua 2 kiểm tra trên, tiến hành update password mới
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    public String forgotPassword(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        //Generate password
        String newPassword = UUID.randomUUID().toString();
        user.setPassword(newPassword);
        userRepository.save(user);

        //Send mail
        mailService.sendMail(user.getEmail(), "Quên mật khẩu", "Mật khẩu mới: " + newPassword);

        return newPassword;
    }

    public String uploadFile(Integer id, MultipartFile file) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        return fileService.uploadFile(user, file);
    }

    public byte[] readFile(Integer id, Integer fileId) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        return fileService.readFile(fileId);
    }

    public List<String> getFiles(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        return fileService.getFiles(id);
    }
}
