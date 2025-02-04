// Cost.java

package top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder;


@lombok.Data
public class Cost {
    private double currentValue;
    private long deltaPerSecond;
    private String id;
    private long itemMixId;
    private long maxDonationAmount;
    private long maxDonationPeriodSeconds;
    private double targetValue;
}