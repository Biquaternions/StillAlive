package me.biquaternions.stillalive.misc;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class Constants {

    public final long STALLED_THRESHOLD = Duration.ofSeconds(2).toMillis();
    public final long TICK_DURATION_MILLIS = 50;
    public final long TICKS_PER_SECOND = Duration.ofSeconds(1).toMillis() / TICK_DURATION_MILLIS;

}
