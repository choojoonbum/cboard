package org.choo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@ToString
@Log4j
public class Criteria {
    private int pageNum;
    private int amount;
    private int limit;
    private String type;
    private String keyword;
    private String order;

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        setLimit();
    }

    public String[] getTypeArr() {
        return type == null ? new String[] {} : type.split("");
    }

    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.amount)
                .queryParam("type", this.type)
                .queryParam("keyword", this.keyword);
        log.info(builder.toUriString());
        return builder.toUriString();
    }

    public void setLimit() {
        limit = (pageNum - 1) * amount;
    }
}
