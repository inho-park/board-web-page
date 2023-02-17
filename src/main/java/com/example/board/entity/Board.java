package com.example.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = "writer")
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    // 연관 관계를 갖는 다른 엔티티들까지의 Join 과정을 줄이기 위해 fetch 설정
    @ManyToOne(fetch = FetchType.LAZY) // 지연(게으른) 로딩 => 정보들을 전부 찾기 귀찮으니 join 을 생략
    private Member writer;

    public void changeTitle(String title) { this.title = title; }
    public void changeContent(String content) { this.content = content; }

}
