package org.choo.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardVO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private int replyCnt;
    private List<BoardAttachVO> attachList;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime modDate;
}
