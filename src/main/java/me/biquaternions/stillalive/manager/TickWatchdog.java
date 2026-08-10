package me.biquaternions.stillalive.manager;

import lombok.Getter;
import lombok.Setter;
import me.biquaternions.stillalive.misc.Constants;

public class TickWatchdog {

    @Getter
    @Setter
    private volatile long lastTickMillis;

    @Getter
    @Setter
    private volatile boolean ready;

    public boolean isStalled() {
        return (System.currentTimeMillis() - this.lastTickMillis) > Constants.STALLED_THRESHOLD;
    }

}
