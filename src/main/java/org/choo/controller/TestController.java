package org.choo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class TestController {
    @GetMapping("/test")
    public String testView(Model model) {
        return "main/test";
    }
}
