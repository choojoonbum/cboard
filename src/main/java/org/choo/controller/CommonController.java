package org.choo.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class CommonController {
    @GetMapping("/accessError")
    public void accessDenied(Authentication auth, Model model) {
        log.info("access Denied : " + auth);
        model.addAttribute("msg", "Access Denied");
    }
}
