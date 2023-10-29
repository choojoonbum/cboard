package org.choo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReplyVO {
    private Long rno;
    private Long bno;
    private String reply;
    private String replyer;
    private Date regDate;
    private Date modDate;
}
