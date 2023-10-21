package org.choo.service;

import lombok.extern.log4j.Log4j;
import org.choo.config.RootConfig;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class BoardServiceTests {
    @Autowired
    private BoardService service;

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("새로작성하는글");
        board.setContent("새로작성하는내용");
        board.setWriter("작성자");
        service.register(board);
        log.info("생성된 게시물의 번호: " + board.getBno());
    }

    @Test
    public void testGetList() {
        service.getList().forEach(log::info);
    }

    @Test
    public void testGet() {
        log.info(service.get(1L));
    }

    @Test
    public void testUpdate() {
        BoardVO board = service.get(1L);

        if (board == null) {
            return;
        }
        board.setTitle("제목 수정합니다.");
        log.info("Modify Result: " + service.modify(board));
    }

    @Test
    public void testDelete() {
        log.info("Remove Result: " + service.remove(1L));
    }
}
