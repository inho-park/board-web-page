package com.example.board.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity{
    @Id
    private String email;

    private String password;

    private String name;
}
