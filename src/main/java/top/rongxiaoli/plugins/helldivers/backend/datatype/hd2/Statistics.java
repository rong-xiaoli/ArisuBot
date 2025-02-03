
// Statistics.java

package top.rongxiaoli.plugins.helldivers.backend.datatype.hd2;

/**
 * The statistics available for the galaxy wide war effort.
 *
 * Statistics，Contains statistics of missions, kills, success rate etc.
 */
@lombok.Data
public class Statistics {
    /**
     * A percentage indicating average accuracy of Helldivers.
     */
    private Long accuracy;
    /**
     * The total amount of automatons killed since start of the season.
     */
    private Long automatonKills;
    /**
     * The total amount of bullets fired
     */
    private Long bulletsFired;
    /**
     * The total amount of bullets hit
     */
    private Long bulletsHit;
    /**
     * The amount of casualties on the side of humanity.
     */
    private Long deaths;
    /**
     * The amount of friendly fire casualties.
     */
    private Long friendlies;
    /**
     * The total amount of Illuminate killed since start of the season.
     */
    private Long illuminateKills;
    /**
     * The amount of missions lost.
     */
    private Long missionsLost;
    /**
     * A percentage indicating how many started missions end in success.
     */
    private Long missionSuccessRate;
    /**
     * The amount of missions won.
     */
    private Long missionsWon;
    /**
     * The total amount of time spent planetside (in seconds).
     */
    private Long missionTime;
    /**
     * The total amount of players present (at the time of the snapshot).
     */
    private Long playerCount;
    /**
     * The amount of revives(?).
     */
    private Long revives;
    /**
     * The total amount of bugs killed since start of the season.
     */
    private Long terminidKills;
    /**
     * The total amount of time played (including off-planet) in seconds.
     */
    private Long timePlayed;
}