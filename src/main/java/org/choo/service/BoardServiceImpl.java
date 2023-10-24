package org.choo.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.choo.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.util.List;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardMapper mapper;

    @Override
    public void register(BoardVO board) {
        log.info("register......" + board);
        mapper.insertSelectKey(board);
    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get....." + bno);
        return mapper.read(bno);
    }

    @Override
    public boolean modify(BoardVO board) {
        log.info("modify......" + board);
        return mapper.update(board) == 1;
    }

    @Override
    public boolean remove(Long bno) {
        log.info("remove......" + bno);
        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria criteria) {
        log.info("getList......");
        criteria.setLimit((criteria.getPageNum() - 1) * criteria.getAmount());
        return mapper.getListWithPaging(criteria);
    }

    @Override
    public int getTotal(Criteria criteria) {
        return mapper.getTotalCount(criteria);
    }
}
