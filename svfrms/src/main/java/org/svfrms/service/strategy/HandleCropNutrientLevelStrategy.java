package org.svfrms.service.strategy;

import org.svfrms.service.ControlRequestService;
import org.svfrms.service.strategy.interfaces.HandleCropNutrientLevelStrategyInterface;

public enum HandleCropNutrientLevelStrategy implements HandleCropNutrientLevelStrategyInterface {
    Tomato {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    },
    Strawberry {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    },
    Lettuce {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    },
    Spinach {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    },
    Chili {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    },
    Cucumber {
        @Override
        public String nutrientLevelLow(String chamber) {
            String message = chamber + " Low Nutrient Level";
            ControlRequestService.addControlRequest(chamber, "Nutrient Injector");
            return message;
        }
    };
}
