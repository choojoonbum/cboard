package org.choo.service;

import org.choo.domain.Criteria;
import org.choo.domain.ReplyPageDTO;
import org.choo.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    public int register(ReplyVO vo);
    public ReplyVO get(Long rno);
    public int modify(ReplyVO vo);
    public int remove(Long rno);
    public int getCountByBno(Long bno);
    public List<ReplyVO> getList(Criteria cri, Long bno);
    public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
