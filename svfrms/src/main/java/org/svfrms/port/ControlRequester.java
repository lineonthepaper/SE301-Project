package org.svfrms.port;

import org.svfrms.entity.Chamber;
import org.svfrms.enums.Control;

public interface ControlRequester {
    void requestControl(Chamber chamber, Control control) throws InterruptedException;
}
