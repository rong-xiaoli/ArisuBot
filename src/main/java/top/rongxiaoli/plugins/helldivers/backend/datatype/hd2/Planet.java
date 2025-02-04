package top.rongxiaoli.plugins.helldivers.backend.datatype.hd2;

import lombok.Data;

import java.util.List;

/**
 * Contains all aggregated information AH has about a planet.
 */
@Data
public class Planet {
    /**
     * The unique identifier ArrowHead assigned to this planet.
     */
    private int index;
    /**
     * The name of the planet, as shown in game.
     */
    private String name;
    /**
     * The name of the sector the planet is in, as shown in game.
     */
    private String sector;
    /**
     * The biome this planet has.
     */
    private Biome biome;
    /**
     * All Hazards that are applicable to this planet.
     */
    private List<Hazard> hazards;
    /**
     * A hash assigned to the planet by ArrowHead, purpose unknown.
     */
    private long hash;
    /**
     * The coordinates of this planet on the galactic war map.
     */
    private Position position;
    /**
     * A list of Index of all the planets to which this planet is connected.
     */
    private List<Integer> waypoints;
    /**
     * The maximum health pool of this planet.
     */
    private long maxHealth;
    /**
     * The current planet this planet has.
     */
    private long health;
    /**
     * Whether this planet is disabled or not, as assigned by ArrowHead.
     */
    private boolean disabled;
    /**
     * The faction that originally owned the planet.
     */
    private String initialOwner;
    /**
     * The faction that currently controls the planet.
     */
    private String currentOwner;
    /**
     * How much the planet regenerates per second if left alone.
     */
    private double regenPerSecond;
    /**
     * Information on the active event ongoing on this planet, if one is active.
     */
    private Event event;
    /**
     * A set of statistics scoped to this planet.
     */
    private Statistics statistics;
    /**
     * A list of Index integers that this planet is currently attacking.
     */
    private List<Integer> attacking;
}
