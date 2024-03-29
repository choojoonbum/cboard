package org.choo.mapper;

import lombok.extern.log4j.Log4j;
import org.choo.config.RootConfig;
import org.choo.domain.BoardVO;
import org.choo.domain.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class BoardMapperTests {
    @Autowired
    private BoardMapper mapper;

    @Test
    public void testGetList() {
        mapper.getList().forEach(log::info);
    }

    @Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글 1");
		board.setContent("새로 작성하는 내용 51");
		board.setWriter("newbie4");
		mapper.insertSelectKey(board);
		log.info(board);
	}

	@Test
	public void testPaging() {
		Criteria criteria = new Criteria(3, 20);
		List<BoardVO> list = mapper.getListWithPaging(criteria);
		list.forEach(log::info);
	}

	@Test
	public void testSearch() {
		Criteria criteria = new Criteria();
		criteria.setKeyword("99");
		criteria.setType("TC");

		List<BoardVO> list = mapper.getListWithPaging(criteria);
		list.forEach(log::info);
	}

}
