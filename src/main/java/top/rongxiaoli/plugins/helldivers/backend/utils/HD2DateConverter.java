package top.rongxiaoli.plugins.helldivers.backend.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class HD2DateConverter {
    public static ZonedDateTime convert(OffsetDateTime beforeConvert, OffsetDateTime delta) {
        if (beforeConvert.getOffset() == delta.getOffset())
            return ZonedDateTime.from(
                    LocalDateTime.ofEpochSecond(beforeConvert.toEpochSecond() + delta.toEpochSecond(), 0, beforeConvert.getOffset()));
        long totalSeconds = beforeConvert.toEpochSecond() + delta.toEpochSecond();
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(delta.getOffset().getTotalSeconds());
        return ZonedDateTime.from(
                LocalDateTime.ofEpochSecond(
                        totalSeconds,
                        0,
                        offset)
        );
    }
}
