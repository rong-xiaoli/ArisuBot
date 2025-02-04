// TacticalAction.java

package top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder;

import java.util.List;

@lombok.Data
public class TacticalAction {
    private List<String> activeEffectIds;
    private List<Cost> cost;
    private String description;
    private List<Long> effectIds;
    private long id32;
    private long mediaId32;
    private String name;
    private long status;
    private long statusExpireAtWarTimeSeconds;
    private String strategicDescription;
}
