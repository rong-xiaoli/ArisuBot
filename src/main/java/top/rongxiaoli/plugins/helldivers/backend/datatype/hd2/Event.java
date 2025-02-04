package top.rongxiaoli.plugins.helldivers.backend.datatype.hd2;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Event，An ongoing event on a Planet.
 */
@lombok.Data
public class Event {
    /**
     * The identifier of the Campaign linked to this event.
     */
    private Long campaignId;
    /**
     * When the event will end.
     */
    private OffsetDateTime endTime;
    /**
     * The type of event.
     */
    private Long eventType;
    /**
     * The faction that initiated the event.
     */
    private String faction;
    /**
     * The health of the Event at the time of snapshot.
     */
    private Long health;
    /**
     * The unique identifier of this event.
     */
    private Long id;
    /**
     * A list of joint operation identifier linked to this event.
     */
    private List<Long> jointOperationIds;
    /**
     * The maximum health of the Event at the time of snapshot.
     */
    private Long maxHealth;
    /**
     * When the event started.
     */
    private OffsetDateTime startTime;
}