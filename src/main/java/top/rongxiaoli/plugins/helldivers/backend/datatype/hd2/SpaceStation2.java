package top.rongxiaoli.plugins.helldivers.backend.datatype.hd2;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Represents a Super Earth Democracy Space Station.
 */
@Data
public class SpaceStation2 {
    /**
     * The unique identifier of the station.
     */
    public long id32;
    /**
     * The planet it's currently orbiting.
     */
    public Planet planet;
    /**
     * When the election for the next planet will end.
     */
    public OffsetDateTime electionEnd;
    /**
     * A set of flags, purpose currently unknown.
     */
    public int flags;
}
