package com.example.demo03.tracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import com.example.demo03.enums.*;

public final class Client {

    // client's name
    private String name = "";
    // client's height tha will be used to calculate their BMI
    private int height = 0;
    // the hashMap where the different tracked data will be stored
    private final HashMap<MeasurementEnum, Measurement> clientMeasurements = new HashMap<>();

    /**
     * Constructor function for object Client
     * @param name client's name
     * @param height client's height
     */
    public Client(String name, int height){
        this.name = name;
        this.height = height;
        initClientDataSet();
    }

    /**
     * Populates the client's hashMap with their data set (Weight, Waist size, etc.)
     */
    public void initClientDataSet() {
        // loop the TrackedData enum and create a Measurement object for every item being tracked
        for (MeasurementEnum measurementEnum : MeasurementEnum.values()) {
            if (measurementEnum == MeasurementEnum.CALORIES) {
                clientMeasurements.put(measurementEnum, new Calories());
            }
            else {
                clientMeasurements.put(measurementEnum, new Measurement(measurementEnum));
            }
        }
    }

    /** Getter method to get a client's data measurements for one type of measurement
     *
     * @param measurement contains measurementEnum representing type of data we want
     * @return Measurement representing the data we want
     */
    public Measurement getMeasurement(MeasurementEnum measurement) {
        return clientMeasurements.get(measurement);
    }

    /** Getter method to get client's name
     *
     * @return String representing client's name
     */
    public String getName() {
        return name;
    }

    /** Getter method to get client's height
     *
     * @return Int representing client's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Print the measurements of one specific body part/data (weight, biceps, waist, etc.)
     * @param measurementEnum contains measurementEnum representing type of data we want
     */
    public String printSpecificClientMeasurement(MeasurementEnum measurementEnum) {
        return clientMeasurements.get(measurementEnum).toString();
    }

    /**
     * getBMI calculates the user's BMI trend, showing how their BMI has changed from their oldest measurement,
     * and predicts the user's future BMI (after a week)
     *
     * the function does not return anything, it only prints how much the user's BMI has changed
     * since measurements started and predicts the user's BMI after a week
     */
    public void getBMI() {

        TreeMap <LocalDate, Float> weightEntries = getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        // for calculateBMIDifference to work as intended we need at one entry for height and weight
        if (!weightEntries.isEmpty()) {

            String[] BMIDifference = calculateBMIDifference(weightEntries, height);

            // print the difference in BMI to the user
            System.out.println("Since " + BMIDifference[0] + ", " + name + "'s BMI has changed from " + BMIDifference[2] + " to " + BMIDifference[3] + ".");

            // try to predict future BMI
            float futureBMI = predictFutureBMI(weightEntries, height);

            // get future date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            LocalDate futureDate = (getMeasurement(MeasurementEnum.WEIGHT).getEntries().lastKey()).plusWeeks(1);

            // only give prediction if enough data was found to make one
            if (futureBMI != -1) {
                // print prediction to user
                System.out.println("By " + futureDate + ", " + name + "'s BMI will be " + futureBMI + ".");
            } else {
                System.out.println("Not enough weight data to make a prediction.");
            }

        } else {
            System.out.println("Not enough data available for " + name + ".");
        }
   }

    /**
     * getCurrentBMI calculates the user's BMI at a certain point given their height and weight.
     * This function is used by getBMI to calculate the BMI trend
     *
     * @param weight (float) in kilograms
     * @param height (float) in centimetres
     * @return BMI (float) based on weight and height
     */
    public static float getCurrentBMI(float weight, float height) {
        // convert centimetres to metres
        height /= 100;
        float BMI = weight / (float) Math.pow(height, 2);
        return roundFloat(BMI, 1);
    }

    /**
     * used by getBMI to predict a user's BMI after a week, based on the user's BMI from one week ago and
     * the user's current BMI
     *
     * @param weightEntries a dated list of weight entries (with at least one entry),
     *                      the key is a date
     *                      the value is the user's weight (on that date) in kilograms
     * @param height in metres (we don't check for the user's height one week ago because height does not change
     *               that much in one week
     * @return -1 if a prediction could not be made, otherwise returns the user's predicted BMI after one week
     */
    public float predictFutureBMI(TreeMap<LocalDate, Float> weightEntries, float height) {
        // get the user's most recent BMI
        // get the most recent date with a weight entry
        LocalDate recentDate = weightEntries.lastKey();
        float recentWeight = weightEntries.get(recentDate);
        float recentBMI = getCurrentBMI(recentWeight, height);

        // get the user's BMI from one week ago
        LocalDate oneWeekAgo = recentDate.minusWeeks(1);
        // get the user's past weight entry
        float pastWeight = -1; // default value is -1
        // check if an entry exists one week ago
        if (weightEntries.containsKey(oneWeekAgo)) {
            pastWeight = weightEntries.get(oneWeekAgo);
        } // check if an entry exists one week ago minus one day
        else if (weightEntries.containsKey(oneWeekAgo.minusDays(1))) {
            pastWeight = weightEntries.get(oneWeekAgo.minusDays(1));
        } // check if an entry exists one week ago plus one day
        else if (weightEntries.containsKey(oneWeekAgo.plusDays(1))) {
            pastWeight = weightEntries.get(oneWeekAgo.plusDays(1));
        } else {
            // return -1 if not enough data exists
            return -1;
        }
        // calculate pastBMI
        float pastBMI = getCurrentBMI(pastWeight, height);

        // extrapolate the future BMI
        float FutureBMI = recentBMI + (recentBMI - pastBMI);
        return roundFloat(FutureBMI, 1);

    }

    /**
     * calculates how much the user's BMI has changed from the first measurement to the most recent measurement
     * @param weightEntries a TreeMap containing weight entries (in kg) attached to their respective dates
     * @param height an int representing the person's height
     * @return a Sting array of length 4
     * arg 0 contains the date of the first weight entry
     * arg 1 contains the date of the last weight entry
     * arg 2 contains the first BMI
     * arg 3 contains the last BMI
     */
    public String[] calculateBMIDifference(TreeMap<LocalDate, Float> weightEntries, int height) {
        // initialize output variable
        String[] output = new String[4];
        // get the first entry
        output[0] = weightEntries.firstKey().format(DateTimeFormatter.ofPattern("dd/MM/yy")); // get the date of the first weight entry
        float oldWeight = weightEntries.firstEntry().getValue();
        output[2] = Float.toString(getCurrentBMI(oldWeight, height));
        // get the last entry
        output[1] = weightEntries.lastKey().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        float newWeight = weightEntries.lastEntry().getValue();
        output[3] = Float.toString(getCurrentBMI(newWeight, height));

        return output;
    }

    /**
     * this function rounds a float to a certain number of decimals, as specified by precision
     * @param num is the float that needs to be rounded
     * @param precision is the number of decimals to round to (at least zero)
     * @return the rounded float
     */
    public static float roundFloat(float num, int precision) {
        if (precision == 0)
            return Math.round(num); // rounds to the nearest integer if precision is zero
        // convert num to a rounded string format
        String strNum = String.format("%." + precision + "f", num);
        // convert back to a float and return the rounded number
        return Float.parseFloat(strNum);
    }

    /**
     * Prints all the measurements of all the data available for the specified client
     */
    public void printAllClientMeasurements() {

        System.out.println("Below is all the data available for " + name);

        for (MeasurementEnum measurementEnum : clientMeasurements.keySet()) {
            System.out.println(measurementEnum.getString());
            System.out.println(clientMeasurements.get(measurementEnum));
        }
    }

    /**
     * This function creates a string from the client's tracked data
     * @return String containing all the data in the client's hashMap
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        // append the client's name to the first line of the String
        stringBuilder.append(name).append("\n");

        // loop the list of measurements and append their string to a new line
        for (MeasurementEnum measurement : clientMeasurements.keySet()) {
            stringBuilder.append(measurement.toString());
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the two objects are the exact same reference
        if (this == obj) {
            return true;
        }

        // Check if the other object is null
        if (obj == null) {
            return false;
        }

        // Check if the two objects are of the same class
        if (getClass() != obj.getClass()) {
            return false;
        }

        // At this point, we can safely cast the object
        Client other = (Client) obj;

        // Compare the name field
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        // Compare the height field
        if (this.height != other.height) {
            return false;
        }

        // Compare the measurement maps
        if (!Objects.equals(this.clientMeasurements, other.clientMeasurements)) {
            return false;
        }

        // All checks passed, the objects are equal
        return true;
    }

    @Override
    public int hashCode() {
        // Use the Objects.hash method to combine the relevant fields
        int result = Objects.hash(name, height, clientMeasurements);
        return result;
    }

}