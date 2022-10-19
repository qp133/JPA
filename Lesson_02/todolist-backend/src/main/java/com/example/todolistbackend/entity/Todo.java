package com.example.todolistbackend.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title")
    private String title;

    //SET DEFAULT VALUE
    //Cách 1: xét mặc định ban đầu = false
    @Column(name = "status")
    private Boolean status;

    //Cách 2: sử dụng lifecycle callbacks: PrePersist
    @PrePersist
    public void prePersist() {
        if(status == null)   {
            status = false;
        }

    }

    //Cách 3:
//    @Column(name = "status", columnDefinition = "boolean default false")
//    private Boolean status;

}