package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KeywordDTO {

    private String name;

    public KeywordDTO(Keyword keyword) {
        this.name = keyword.getName();
    }

}
