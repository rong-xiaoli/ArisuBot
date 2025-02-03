package top.rongxiaoli.plugins.helldivers.backend.datatype.hd2;

/**
 * Hazard，Describes an environmental hazard that can be present on a Planet.
 */
@lombok.Data
public class Hazard {
    /**
     * The description of the environmental hazard.
     */
    private String description;
    /**
     * The name of this environmental hazard.
     */
    private String name;
}