package com.example.demo03.enums;


public enum MeasurementEnum {
    WEIGHT("Weight (kg)"),
    BICEP("Biceps size (cm)"),
    WAIST("Waist size (cm)"),
    CHEST("Chest size (cm)"),
    THIGHS("Thighs size (cm)"),
    CALORIES("Calorie intake (cm)");

    private final String trackedDataString;

    MeasurementEnum(String trackedDataString) {
        this.trackedDataString = trackedDataString;
    }

    /**
     * This returns the String associated with the enum so that we can
     * use is as a "name" for the object Measurement
     * @return
     */
    public String getString() {
        return trackedDataString;
    }

    public static MeasurementEnum getEnumAt(int index) {
        return MeasurementEnum.values()[index];
    }

    /**
     * This gets the MeasurementEnum from the string
     * Used by Reader
     * @param enumName is the variable name of the enum (eg: "WEIGHT")
     */
    public static MeasurementEnum getEnumByName(String enumName) {return MeasurementEnum.valueOf(enumName);}
}
