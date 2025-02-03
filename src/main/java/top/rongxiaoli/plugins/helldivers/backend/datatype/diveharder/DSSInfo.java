package top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder;

import java.util.List;

/**
 * Request
 */
@lombok.Data
public class DSSInfo {
    private Long currentElectionEndWarTime;
    private String currentElectionId;
    private Long flags;
    private Long id32;
    private String lastElectionId;
    private Long planetIndex;
    private List<TacticalAction> tacticalActions;
}