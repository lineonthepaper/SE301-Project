package org.svfrms.request;

import org.svfrms.enums.Control;

public record ControlRequest(String chamber, Control control) {
}