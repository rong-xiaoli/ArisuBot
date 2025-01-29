package top.rongxiaoli.plugins.helldivers.backend.datatype;

import lombok.Data;
import lombok.Getter;
@Data
@Getter
public class NewsFeedItem {
    private int id,published,type;
    private String message;
}
