package org.choo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.choo.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j
@RequestMapping("/admin/board")
@AllArgsConstructor
public class AdminBoardController {
    private BoardService service;

    @GetMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("list", service.getList());
    }

    @GetMapping("/register")
    public void register(@ModelAttribute("formData") BoardVO board) {
        log.info("register");
    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {
        log.info("register: " + board);
        service.register(board);
        rttr.addFlashAttribute("result", board.getBno());
        return "redirect:/admin/board/list";
    }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("criteria") Criteria criteria, Model model) {
        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, RedirectAttributes rttr) {
        log.info("modify: " + board);
        if (service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/admin/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
        log.info("remove....." + bno);

        if (service.remove(bno)) {
            rttr.addFlashAttribute("result", "success");
        }

        return "redirect:/admin/board/list";
    }
}