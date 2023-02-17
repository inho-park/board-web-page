package com.example.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;


    @ManyToOne
    private Board board;
}
