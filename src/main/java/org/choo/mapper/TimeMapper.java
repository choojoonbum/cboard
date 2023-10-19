package org.choo.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
    @Select("SELECT date_format(Now(), '%Y-%m-%d')")
    public String getTime();

    public String getTime2();
}
