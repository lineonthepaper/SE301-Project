package org.svfrms.request;

import org.svfrms.entity.Control;

public record ControlRequest(String chamber, Control control) {
}