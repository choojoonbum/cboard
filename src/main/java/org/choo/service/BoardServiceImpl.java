package org.choo.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.choo.domain.BoardAttachVO;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.choo.mapper.BoardAttachMapper;
import org.choo.mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.util.List;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardMapper mapper;
    private BoardAttachMapper attachMapper;

    @Override
    @Transactional
    public void register(BoardVO board) {
        log.info("register......" + board);
        mapper.insertSelectKey(board);
        if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
            return;
        }
        board.getAttachList().forEach(attach -> {
            attach.setBno(board.getBno());
            attachMapper.insert(attach);
        });
    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get....." + bno);
        return mapper.read(bno);
    }

    @Transactional
    @Override
    public boolean modify(BoardVO board) {
        log.info("modify......" + board);
        attachMapper.deleteAll(board.getBno());
        boolean modifyResult = mapper.update(board) == 1;

        if (modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
            board.getAttachList().forEach(attach -> {
                attach.setBno(board.getBno());
                attachMapper.insert(attach);
            });
        }
        return modifyResult;
    }

    @Override
    @Transactional
    public boolean remove(Long bno) {
        log.info("remove......" + bno);
        attachMapper.deleteAll(bno);
        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria criteria) {
        log.info("getList......");
        criteria.setLimit();
        return mapper.getListWithPaging(criteria);
    }

    @Override
    public int getTotal(Criteria criteria) {
        return mapper.getTotalCount(criteria);
    }

    @Override
    public List<BoardAttachVO> getAttachList(Long bno) {
        log.info("get Attach list by bno" + bno);
        return attachMapper.findByBno(bno);
    }
}
