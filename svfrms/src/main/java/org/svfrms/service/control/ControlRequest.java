package org.svfrms.service.control;

import org.svfrms.enums.Control;
import org.svfrms.request.Command;

public record ControlRequest(String chamber, Control control, ControlService controlService) implements Command {
    @Override
    public void execute() {
        controlService.applyControl(chamber, control);
    }
}
