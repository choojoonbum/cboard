package org.choo.controller;

import lombok.extern.log4j.Log4j;
import org.choo.domain.SampleVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {
    @GetMapping(value = "/getSample1", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public SampleVo getSample1() {
        return new SampleVo(112,"스타","로드");
    }
    @GetMapping(value = "/getSample2", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SampleVo getSample2() {
        return new SampleVo(112,"스타","로드");
    }
    @GetMapping(value = "/getSample3", produces = {MediaType.APPLICATION_XML_VALUE})
    public SampleVo getSample3() {
        return new SampleVo(112,"스타","로드");
    }
    @GetMapping(value = "/check", params = {"height","weight"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleVo> check(Double height, Double weight) {
        SampleVo vo = new SampleVo(0, "" + height , "" + weight);
        ResponseEntity<SampleVo> result = null;
        if (height < 150) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        } else {
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }

    /* 시큐리티 TEST */
    @GetMapping("/all")
    public void doAll() {
        log.info("do all can access everybody");
    }

    @GetMapping("/member")
    public void doMember() {
        log.info("logined member");
    }

    @GetMapping("/admin")
    public void doAdmin() {
        log.info("admin only");
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    @GetMapping("/annoMember")
    public void doMember2() {
        log.info("logined annotation member");
    }

    //@Secured({"ROLE_ADMIN"})
    @GetMapping("/annoAdmin")
    public void doAdmin2() {
        log.info("admin annotation only");
    }
}
