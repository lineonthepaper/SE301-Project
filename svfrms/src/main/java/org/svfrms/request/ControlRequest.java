package org.svfrms.request;

import org.svfrms.enums.Control;
import org.svfrms.service.control.ControlService;

public record ControlRequest(String chamber, Control control, ControlService controlService) implements Command {
    @Override
    public void execute() {
        controlService.applyControl(chamber, control);
    }
}
