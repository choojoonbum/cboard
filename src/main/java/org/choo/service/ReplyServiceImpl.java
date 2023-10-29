package org.choo.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.Criteria;
import org.choo.domain.ReplyPageDTO;
import org.choo.domain.ReplyVO;
import org.choo.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j
public class ReplyServiceImpl implements ReplyService {
    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        return mapper.update(vo);
    }

    @Override
    public int remove(Long rno) {
        return mapper.delete(rno);
    }

    @Override
    public int getCountByBno(Long bno) {
        return mapper.getCountByBno(bno);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {
        log.info("get Reply List of a Board " + bno);
        return mapper.getListWithPaging(cri, bno);
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long bno) {
        return new ReplyPageDTO(getCountByBno(bno), mapper.getListWithPaging(cri, bno));
    }
}
