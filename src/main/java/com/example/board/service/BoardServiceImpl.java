package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final BoardRepository repository;
    private final ReplyRepository replyRepository;

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        Function<Object[], BoardDTO> fn = (entity -> entityToDTO((Board)entity[0],
                                                                (Member)entity[1],
                                                                (Long)entity[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO(result, fn);
    }

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        return repository.save(dtoToEntity(dto)).getBno();
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] result = (Object[]) repository.getBoardByBno(bno);
        return entityToDTO((Board) result[0], (Member) result[1], (Long) result[2]);
    }

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        repository.deleteById(bno);
    }

    @Override
    @Transactional
    public void modify(BoardDTO boardDTO) {
        // getOne -> 필요한 순간까지 로딩을 지연하는 방식
        // 현재는 deprecated 되어 getReferenceById() 를 사용
        Board board = repository.getReferenceById(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        repository.save(board);
    }
}
