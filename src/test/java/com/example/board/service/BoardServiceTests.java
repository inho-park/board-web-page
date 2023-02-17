package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test..")
                .content("testtestestsestestesttete")
                .writerEmail("user 100@aaa.com")
                .build();

        System.out.println(service.register(dto));
    }

    @Test
    public void testGet() {
        System.out.println(service.get(100l));

    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);
        for (BoardDTO boardDTO : result.getDtoList()) System.out.println(boardDTO);
    }

    @Test
    public void testRemove() {
        service.removeWithReplies(2l);
    }

    // 왜 안되는 지 모르겠음 ( 영속성 문제라는데 조금 더 봐야 알듯 )
    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3l)
                .title("변경된 제목")
                .content("변경된 내용")
                .build();

        service.modify(boardDTO);
    }
}
