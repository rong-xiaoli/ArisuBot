package top.rongxiaoli.plugins.helldivers.backend.datatype;

import lombok.Getter;

@Getter
public class NewsFeedItem {
    private int id,published,type;
    private String message;
}
