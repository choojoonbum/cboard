package org.choo.mapper;

import org.choo.domain.MemberVO;

public interface MemberMapper {
    public MemberVO read(String userid);
}
