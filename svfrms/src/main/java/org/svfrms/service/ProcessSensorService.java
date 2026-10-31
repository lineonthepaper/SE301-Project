package org.svfrms.service;

import java.util.Map;

import org.svfrms.entity.Chamber;
import org.svfrms.entity.ChamberState;
import org.svfrms.entity.CropProfile;
import org.svfrms.enums.Breach;
import org.svfrms.enums.Control;
import org.svfrms.enums.Crop;
import org.svfrms.enums.SensorType;
import org.svfrms.port.AlertSink;
import org.svfrms.port.ControlRequester;
import org.svfrms.port.SensorLog;
import org.svfrms.port.UsageRecorder;
import org.svfrms.util.SensorLimits;

public class ProcessSensorService {
    private final Chamber chamber;
    private final ControlRequester controlRequester;

    private final ChamberState state;
    private final SensorLog sensorLog;
    private final AlertSink alertSink;
    private final UsageRecorder usageRecorder;

    public ProcessSensorService(Chamber chamber, ChamberState state,
            ControlRequester controlRequester, SensorLog sensorLog, AlertSink alertSink, UsageRecorder usageRecorder) {
        this.chamber = chamber;
        this.controlRequester = controlRequester;

        this.state = state;
        this.sensorLog = sensorLog;
        this.alertSink = alertSink;
        this.usageRecorder = usageRecorder;
    }

    private void printProcessSensorLog(String sensor, double value) {
        sensorLog.addLog("Processing " + chamber.crop().getLabel() + " " + sensor + " = " + value);
    }

    private String alertMessage(SensorType sensor, Breach breach) {
        String subject = sensor.isCropSpecific() ? chamber.name() + " " + chamber.crop().getLabel() : chamber.name();
        return subject + " " + breach.getLabel() + " " + sensor.getAlertName();
    }

    public void process(SensorType sensor, double value) throws InterruptedException {
        printProcessSensorLog(sensor.getLogName(), value);
        state.set(sensor, value);

        Crop cropEnum = sensor.isCropSpecific() ? chamber.crop() : Crop.DEFAULT;
        CropProfile cropProfile = cropEnum.getCropProfile();
        Map<SensorType, SensorLimits> limitsMap = cropProfile.limits();

        if (!limitsMap.containsKey(sensor)) {
            return;
        }

        SensorLimits limits = limitsMap.get(sensor);
        Breach breach = value > limits.getHigh() ? Breach.HIGH : value < limits.getLow() ? Breach.LOW : null;

        if (breach == null) {
            return;
        }

        String message = alertMessage(sensor, breach);
        alertSink.addAlert(chamber, message);

        usageRecorder.recordBreachUsage(cropProfile, sensor, breach);

        Control control = sensor.getControlToUse(breach);

        if (control != null) {
            controlRequester.requestControl(chamber, control);
        }
    }
}
