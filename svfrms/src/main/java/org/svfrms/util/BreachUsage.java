package org.svfrms.util;

import org.svfrms.enums.Breach;

public record BreachUsage(int high, int low) {
    public int amountFor(Breach breach) {
        return breach == Breach.HIGH ? high : low;
    }
}
