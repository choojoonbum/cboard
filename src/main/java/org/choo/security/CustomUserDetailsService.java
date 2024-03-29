package org.choo.security;

import lombok.extern.log4j.Log4j;
import org.choo.domain.MemberVO;
import org.choo.mapper.MemberMapper;
import org.choo.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.warn("Load User By UserName: " + userName);
        MemberVO vo = memberMapper.read(userName);

        log.warn("queried by member mapper: " + vo);
        return vo == null ? null : new CustomUser(vo);
    }
}
