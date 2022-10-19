package com.example.demo.entity;

import com.example.demo.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@NamedNativeQuery(
        name = "getUserDtoByEmailUsingNativeQuery",
        query = "select u.id, u.name, u.email from user u where u.email = ?1",
        resultSetMapping = "userInfo"
)
@SqlResultSetMapping(
        name = "userInfo",
        classes = @ConstructorResult(
                targetClass = UserDto.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "email")
                }
        )
)

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//Nếu không chỉ định name, tên của entity trùng với tên của class
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth")
    private LocalDate birth;

    @PrePersist
    public void prePersist() {
        birth = LocalDate.now().minusYears(age);
    }
}
