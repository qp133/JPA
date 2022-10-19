package com.example.demo.repository;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserInfo;
import com.example.demo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    //Cách 1: Sử dụng query method
    List<User> getByNameContainsIgnoreCase(String name);

    boolean existsByEmailIgnoreCase(String email);

    void deleteByEmail(String email);

    User getUserById(Integer id);

    //Trả về trực tiếp ở dạng Dto
    User getUserByEmail(String email);

    //Trả về Dto qua cách JPA QL
    @Query("select new com.example.demo.dto.UserDto(u.id, u.name, u.email) from User u where u.email = ?1")
    UserDto getUserDtoByEmail(String email);

    //Trả về Dto qua Projection
    //Bản chất của projection là Interface chứa các phương thức để lấy ra thông tin cần thiết về đối tượng
    UserInfo getUserInfoByEmail(String email);

    //Tr về Dto qua Native query
    @Query(nativeQuery = true, name = "getUserDtoByEmailUsingNativeQuery")
    UserDto getUserDtoByEmailUsingNativeQuery(String email);


    //Cách 2: Sử dụng JPQL (JPA Query Language)
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User getUserDetailById(Integer id);

    @Query("SELECT u FROM User u WHERE u.id=:id")
    User getUserDetailByIdOther(@Param("id") Integer id);


    //Cách 3: Sử dụng Native query
    @Query(nativeQuery = true, value = "select * from user where id = ?1")
    User getUserByIdUsingNativeQuery(Integer id);

    //Thay đổi tên của 1 user (update tên)
    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2 where u.id = ?1")
    void updateUserName(Integer id, String name);

    //Sắp xếp
    List<User> getByOrderByAgeAsc();

    @Query(value = "SELECT * FROM user ORDER BY age", nativeQuery = true)
    public List<User> getListUserOrderByNameAsc();

}
