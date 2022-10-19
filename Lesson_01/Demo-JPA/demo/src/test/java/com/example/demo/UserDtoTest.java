package com.example.demo;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserInfo;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDtoTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void test_getUserDtoUsingCustomMapper() {
        User user = userRepository.getUserById(10);
        UserDto userDto = UserMapper.toUserDto(user);
        System.out.println(userDto);
    }

    @Test
    void test_getUserDtoUsingModelMapper() {
        User user = userRepository.getUserById(10);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        System.out.println(userDto);
    }

    @Test
    void test_getUserByEmail() {
        User user = userRepository.getUserByEmail("ina.barrows@yahoo.com");
        System.out.println(user);
    }

    @Test
    void test_getUserDtoByEmail() {
        UserDto userDto = userRepository.getUserDtoByEmail("ina.barrows@yahoo.com");
        System.out.println(userDto);
    }

    @Test
    void test_getUserInfoByEmail() {
        UserInfo userInfo = userRepository.getUserInfoByEmail("ina.barrows@yahoo.com");
        System.out.println(userInfo.getId() + " - " + userInfo.getName() + " - " + userInfo.getEmail());
    }

    @Test
    void test_getUserDtoByEmailUsingNativeQuery() {
        UserDto userDto = userRepository.getUserDtoByEmailUsingNativeQuery("ina.barrows@yahoo.com");
        System.out.println(userDto);
    }
}
