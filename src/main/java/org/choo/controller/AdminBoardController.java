package org.choo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.choo.domain.PageDTO;
import org.choo.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Log4j
@RequestMapping("/admin/board")
@AllArgsConstructor
public class AdminBoardController {
    private BoardService service;

    @GetMapping("/list")
    public void list(Criteria criteria, Model model) {
        log.info("list");
        int total = service.getTotal(criteria);
        Map<String, String> type = new LinkedHashMap();
        String[][] typeArray = {{"T", "제목"},{"C", "내용"},{"W", "작성자"},{"TC", "제목 or 내용"},{"TW", "제목 or 작성자"},{"TCW", "제목 or 내용 or 작성자"}};
        for (int i = 0; i < typeArray.length; i++) {
            type.put(typeArray[i][0], typeArray[i][1]);
        }
        model.addAttribute("amount", new int[] {10,25,50,100});
        model.addAttribute("type", type);
        model.addAttribute("criteria", criteria);
        model.addAttribute("list", service.getList(criteria));
        model.addAttribute("pageMaker", new PageDTO(criteria, total));
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
    public String modify(BoardVO board, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes rttr) {
        log.info("modify: " + board);
        if (service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/admin/board/list" + criteria.getListLink();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes rttr) {
        log.info("remove....." + bno);

        if (service.remove(bno)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/admin/board/list" + criteria.getListLink();
    }
}
