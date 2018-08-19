package com.mgr.twitteranalyser.graph.model;

import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Node {

    private final long id;
    private final String caption;

    public Node(TwitterUser user) {
        this.id = user.getId();
        this.caption = user.getScreenName();
    }

    public Node(Keyword keyword) {
        this.id = keyword.getId();
        this.caption = keyword.getName();
    }

}
