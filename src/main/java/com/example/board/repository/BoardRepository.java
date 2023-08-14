package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    // 한개의 로우 (Object) 내에 Object[] 로 나옴
    @Query("SELECT b, w FROM Board b LEFT OUTER JOIN b.writer w WHERE b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    // 중간에 on이 사용되면서 조인 조건을 직접 지정하는 부분이 추가되었음
    @Query("SELECT b, r FROM Board b LEFT OUTER JOIN Reply r ON r.board = b WHERE b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(
            value = "SELECT b, w, count(r) " +
            "FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN Reply r ON r.board = b " +
            "GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b"
    )
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            "FROM Board b LEFT JOIN b.writer w " +
            "LEFT OUTER JOIN Reply r ON r.board = b " +
            "WHERE b.bno =:bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
