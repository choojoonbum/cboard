package org.choo.controller;

import lombok.extern.log4j.Log4j;
import org.choo.domain.SampleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;

@Controller
@RequestMapping("/sample")
@Log4j
public class SampleController {
    @GetMapping("")
    public void basic() {
        log.info("basic......");
    }

    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06() {
        log.info("/ex06............");
        SampleDTO dto = new SampleDTO();
        dto.setAge(30);
        dto.setName("홍길동");
        return dto;
    }

    @GetMapping("/exUpload")
    public void exUpload() {
        log.info("/exUpload......");
    }

    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files) {
        files.forEach(file -> {
            log.info("---------------");
            log.info("name:" + file.getOriginalFilename());
            log.info("size:" + file.getSize());
        });
    }


}
