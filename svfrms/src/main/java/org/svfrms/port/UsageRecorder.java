package org.svfrms.port;

import org.svfrms.entity.CropProfile;
import org.svfrms.enums.Breach;
import org.svfrms.enums.SensorType;

public interface UsageRecorder {
    void recordBreachUsage(CropProfile cropProfile, SensorType sensor, Breach breach);
}
