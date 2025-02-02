package top.rongxiaoli.plugins.helldivers.backend.datatype;

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
    public int index;
    /**
     * The name of the planet, as shown in game.
     */
    public String name;
    /**
     * The name of the sector the planet is in, as shown in game.
     */
    public String sector;
    /**
     * The biome this planet has.
     */
    public Biome biome;
    /**
     * All Hazards that are applicable to this planet.
     */
    public List<Hazard> hazards;
    /**
     * A hash assigned to the planet by ArrowHead, purpose unknown.
     */
    public long hash;
    /**
     * The coordinates of this planet on the galactic war map.
     */
    public Position position;
    /**
     * A list of Index of all the planets to which this planet is connected.
     */
    public List<Integer> waypoints;
    /**
     * The maximum health pool of this planet.
     */
    public long maxHealth;
    /**
     * The current planet this planet has.
     */
    public long health;
    /**
     * Whether this planet is disabled or not, as assigned by ArrowHead.
     */
    public boolean disabled;
    /**
     * The faction that originally owned the planet.
     */
    public String initialOwner;
    /**
     * The faction that currently controls the planet.
     */
    public String currentOwner;
    /**
     * How much the planet regenerates per second if left alone.
     */
    public double regenPerSecond;
    /**
     * Information on the active event ongoing on this planet, if one is active.
     */
    public Event event;
    /**
     * A set of statistics scoped to this planet.
     */
    public Statistics statistics;
    /**
     * A list of Index integers that this planet is currently attacking.
     */
    public List<Integer> attacking;
}
