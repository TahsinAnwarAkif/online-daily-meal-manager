package net.therap.OnlineDailyMealManager.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anwar
 * @since 1/10/18
 */
public class Constants {

    public enum DAYS {
        SUN, MON, TUE, WED, THU;

        public static List<String> getAllDays() {
            List<String> days = new ArrayList<>();
            for (DAYS day : DAYS.values()) {
                days.add(String.valueOf(day));
            }
            return days;
        }

        public static boolean contains(String inputDay) {
            for (DAYS day : DAYS.values())
                if (day.name().equals(inputDay.toUpperCase())) {
                    return true;
                }
            return false;
        }
    }

    public enum METHODS {
        GET, POST, PUT, DELETE;
    }

    public enum CHECKS {
        CHECK_IF_NOT_AVAILABLE, CHECK_IF_ALREADY_EXISTS;
    }

    public enum DB_OPERATIONS {
        SAVE_OR_UPDATE_OPERATION, DELETE_OPERATION;
    }

    public enum BUTTONS {
        CREATE_BUTTON("Create"), UPDATE_BUTTON("Update"), DELETE_BUTTON("Delete"),
        SHOW_ITEMS_BUTTON("Show Available Items"), SHOW_ITEM_MEAL_BUTTON("Search"), SHOW_A_DAYS_MEAL_BUTTON("Meal Times");

        private String buttonName;

        BUTTONS(String buttonName) {
            this.buttonName = buttonName;
        }

        public String getName() {
            return this.buttonName;
        }
    }

    public enum OPERATION_RESULTS {
        OK("ok"), ITEM_NOT_AVAILABLE_ERROR("Item is not available!\n"), ITEM_ALREADY_EXISTS_ERROR("Item already exists\n"),
        ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS("Item is not available or already exists!\n"),
        MEAL_NOT_AVAILABLE_ERROR("Meal is not available!\n"), MEAL_ALREADY_EXISTS_ERROR("Meal already exists\n"),
        MEAL_NOT_AVAILABLE_OR_ALREADY_EXISTS("Meal is not available or already exists!\n"),
        ITEM_MEAL_NOT_AVAILABLE_ERROR("Item is not available in this day's meal!\n"),
        ITEM_MEAL_ALREADY_EXISTS_ERROR("Item already exists in this day's meal!\n"),
        ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS_IN_MEAL_ERROR("Item is not available or already exists in this day's meal\n"),
        USER_NOT_AVAILABLE_ERROR("Username and password did not match!\n"),
        BLANK_INPUT_ERROR("Input cannot be empty!\n"), INVALID_DAY_ERROR("Please enter first 3 letters of a valid working day"),
        ENTITY_MANAGER_FACTORY_BUILD_FAILED_ERROR("Entity Manager creation failed for "),
        NULL_DELETION_ERROR("Please select at least one to remove!\n"),
        NULL_CREATION_ERROR("Please select at least one to create!\n");

        private String message;

        OPERATION_RESULTS(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public static OPERATION_RESULTS getEnumByMessage(String inputString) {
            for (OPERATION_RESULTS result : OPERATION_RESULTS.values())
                if (inputString.equals(result.message)) {
                    return result;
                }
            return null;
        }
    }

    public enum UTILITY_CHECKS {
        BLANK_INPUT(""), ALL_ITEM_MEAL("all"), ALERT_IF_SUCCESS("{name} {operation} successfully!");

        private String value;

        UTILITY_CHECKS(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
