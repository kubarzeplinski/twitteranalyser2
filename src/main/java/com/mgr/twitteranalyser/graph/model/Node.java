package com.mgr.twitteranalyser.graph.model;

import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

//TODO remove
@Deprecated
@AllArgsConstructor
@Getter
public class Node {

    private final String caption;
    private final long id;

    public Node(TwitterUser user) {
        this.caption = user.getScreenName();
        this.id = user.getId();
    }

    public Node(Keyword keyword) {
        this.caption = keyword.getName();
        this.id = keyword.getId();
    }

}
