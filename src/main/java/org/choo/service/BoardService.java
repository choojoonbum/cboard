package org.choo.service;

import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;

import java.util.List;

public interface BoardService {
    public void register(BoardVO board);

    public BoardVO get(Long bno);

    public boolean modify(BoardVO board);

    public boolean remove(Long bno);

    public List<BoardVO> getList(Criteria criteria);

    public int getTotal(Criteria criteria);
}
