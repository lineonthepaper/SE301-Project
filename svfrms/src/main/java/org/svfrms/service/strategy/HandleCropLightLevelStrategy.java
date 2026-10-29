package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropLightLevelStrategyInterface;

public enum HandleCropLightLevelStrategy implements HandleCropLightLevelStrategyInterface {
    Tomato {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    },
    Strawberry {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    },
    Lettuce {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    },
    Spinach {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    },
    Chili {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    },
    Cucumber {
        @Override
        public String lightLevelLow(String chamber) {
            String message = chamber + " Low Light Level";
            ControlRequestService.addControlRequest(chamber, "Grow Light");
            return message;
        }
    };
}
