package org.choo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.Criteria;
import org.choo.domain.ReplyPageDTO;
import org.choo.domain.ReplyVO;
import org.choo.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
    private ReplyService service;

    @PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
        log.info("Reply: " + vo);
        int insertCount = service.register(vo);
        log.info("Reply Insert Count: " + insertCount);
        return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReplyVO> get(@PathVariable Long rno) {
        log.info("get: " + rno);
        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> remove(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        log.info("remove: " + rno);
        return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> modify(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        vo.setRno(rno);
        log.info("rno: " + rno);
        log.info("modify: " + vo);
        return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReplyPageDTO> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
        Criteria cri = new Criteria(page, 10);
        log.info("get Reply List bno: " + bno);
        return new ResponseEntity<>(service.getListPage(cri, bno),HttpStatus.OK);
    }

    @GetMapping(value = "/{bno}/count", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> getList(@PathVariable("bno") Long bno) {
        log.info("get Reply List bno: " + bno);
        return new ResponseEntity<>("" + service.getCountByBno(bno),HttpStatus.OK);
    }
}
