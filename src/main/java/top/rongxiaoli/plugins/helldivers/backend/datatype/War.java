// War.java

package top.rongxiaoli.plugins.helldivers.backend.datatype;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Request
 *
 * War，Global information of the ongoing war.
 */
@lombok.Data
public class War {
    /**
     * The minimum game client version required to play in this war.
     */
    private String clientVersion;
    /**
     * When this war will end (or has ended).
     */
    private OffsetDateTime ended;
    /**
     * A list of factions currently involved in the war.
     */
    private List<String> factions;
    /**
     * A fraction used to calculate the impact of a mission on the war effort.
     */
    private Double impactMultiplier;
    /**
     * The time the snapshot of the war was taken, also doubles as the timestamp of which all
     * other data dates from.
     */
    private OffsetDateTime now;
    /**
     * When this war was started.
     */
    private OffsetDateTime started;
    /**
     * The statistics available for the galaxy wide war effort.
     */
    private Statistics statistics;
}
