package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list.............................." + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }


    @GetMapping("/register")
    public void register() {
        log.info("register get........................");
    }
    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);
        redirectAttributes.addFlashAttribute("msg", boardService.register(dto));
        return "redirect:/board/list";
    }


    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                     Model model,
                     Long bno
    ){
        log.info("read get.........................bno : " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto",boardDTO);
    }


    @PostMapping("/remove")
    public String remove(long bno,
                         RedirectAttributes redirectAttributes
    ) {
        log.info("remove post.........................bno : " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }


    @PostMapping("/modify")
    public String registerPost(BoardDTO dto,
                               RedirectAttributes redirectAttributes,
                               @ModelAttribute("requestDTO") PageRequestDTO requestDTO
    ) {
        log.info("modify post..........................dto : " + dto);
        boardService.modify(dto);
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        return "redirect:/board/read";
    }

}
