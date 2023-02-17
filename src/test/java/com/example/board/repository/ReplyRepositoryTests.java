package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository repository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;
            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply........" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            repository.save(reply);
        });
    }

    @Test
    public void readReply1() {
        Optional<Reply> result = repository.findById(1l);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
}
