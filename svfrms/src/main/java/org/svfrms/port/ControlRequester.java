package org.svfrms.port;

import org.svfrms.enums.Control;

public interface ControlRequester {
    void requestControl(String chamber, Control control) throws InterruptedException;
}
