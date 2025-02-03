// Cost.java

package top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder;


@lombok.Data
public class Cost {
    public double currentValue;
    public long deltaPerSecond;
    public String id;
    public long itemMixId;
    public long maxDonationAmount;
    public long maxDonationPeriodSeconds;
    public double targetValue;
}