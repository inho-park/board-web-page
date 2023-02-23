package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository repository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().email("user " + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title" + i)
                    .content("Content " + i)
                    .writer(member)
                    .build();

            repository.save(board);
        });
    }

    @Test
    @Transactional // LAZY Loading 처리시 사용 ( 하나의 트랜잭션으로 처리하기 위함 )
    // Test 결과를 보면 Hibernate 가 데이터베이스에 조회를 2번 시도한 것을 볼 수 있음 -> 하나의 트랜잭션으로 처리
    public void testRead1() {

        Optional<Board> result = repository.findById(100l);

        Board board = result.get();

        System.out.println(board);
        // 지연 로딩 FetchType.LAZY 로 설정할 시 board 외에 별다른 join 과정을 거치지 않아
        // getWriter() 에서 필요한 Member 테이블을 로딩하기 전에 데이터베이스와 연결이 종료
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter() {
        Object[] result = (Object[])repository.getBoardWithWriter(100l);
        System.out.println("===================================================");
        System.out.println(Arrays.toString(result));
    }

    @Test
    public void testGetBoardWithReply() {
        for (Object[] i : repository.getBoardWithReply(100l)) System.out.println(Arrays.toString(i));
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(10, 10, Sort.by("bno").descending());
        repository.getBoardWithReplyCount(pageable).get().forEach(
                i -> System.out.println(Arrays.toString((Object[])i))
        );
    }

    @Test
    public void testReadByBno() {
        System.out.println(
                Arrays.toString((Object[])repository.getBoardByBno(100l))
        );
    }

    @Test
    public void testSearch1() {
        repository.search1();
    }

    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0,
                                            10,
                                                 Sort.by("bno").descending()
                                                        .and(Sort.by("title").ascending()));
        Page<Object[]> result = repository.searchPage("t","3",pageable);
    }
}
