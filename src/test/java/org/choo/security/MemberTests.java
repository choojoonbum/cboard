package org.choo.security;

import org.choo.config.RootConfig;
import org.choo.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
public class MemberTests {
    @Autowired
    private PasswordEncoder pwEncoder;
    @Autowired
    private DataSource ds;

    @Test
    public void TestInsertMember() {
        String sql = "insert into c_member (userid,userpw,username) values (?,?,?)";
        for (int i = 0; i < 100; i++) {
            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(2, pwEncoder.encode("pw" + i));

                if (i < 80) {
                    pstmt.setString(1, "user" + i);
                    pstmt.setString(3, "일반사용자" + i);

                } else if (i < 90) {
                    pstmt.setString(1, "manager" + i);
                    pstmt.setString(3, "운영자" + i);
                } else {
                    pstmt.setString(1, "admin" + i);
                    pstmt.setString(3, "관리자" + i);
                }
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
                if (con != null) try { con.close(); } catch (SQLException e) {}
            }
        }
    }

    @Test
    public void testInsertAuth() {
        String sql = "insert into c_member_auth (userid, auth) values (?,?)";
        for (int i = 0; i < 100; i++) {
            Connection con = null;
            PreparedStatement pstmt = null;
            try {
                con = ds.getConnection();
                pstmt = con.prepareStatement(sql);

                if (i < 80) {
                    pstmt.setString(1, "user" + i);
                    pstmt.setString(2, "ROLE_USER");

                } else if (i < 90) {
                    pstmt.setString(1, "manager" + i);
                    pstmt.setString(2, "ROLE_MEMBER");
                } else {
                    pstmt.setString(1, "admin" + i);
                    pstmt.setString(2, "ROLE_ADMIN");
                }
                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
                if (con != null) try { con.close(); } catch (SQLException e) {}
            }
        }
    }
}
