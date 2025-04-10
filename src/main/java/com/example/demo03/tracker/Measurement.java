package com.example.demo03.tracker;

import com.example.demo03.enums.MeasurementEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

public class Measurement {

    // the date to be associated with the entries
    protected TreeMap<LocalDate, Float> entries = new TreeMap<>();
    // the Enum associated with the Measurement object;
    private MeasurementEnum measurementEnum;

    public Measurement(MeasurementEnum measurementEnum) {
        this.measurementEnum = measurementEnum;
    }

    /**
     * Adds a measurement, along with the date, to the TreeMap of the data being tracked
     * @param date LocalDate (dd/MM/yy) the date to be associated with the entries
     * @param entry Float value associated with the measurement
     */
    public void addEntry(LocalDate date, Float entry) {
        entries.put(date, entry);
    }

    /** Getter method for the entry treemap
     *
     * @return the entries treemap
     */
    public TreeMap<LocalDate,Float> getEntries() {
        return entries;
    }

    /**
     * calculateTrend calculates the predicted change in a client's measurement within 30 days of the newest weight measurement, using data
     * from the client's change in weight from their weight one week ago and their newest weight
     *
     * @return Float representing the client's measurement prediction 30 days from now
     */
    public float calculateTrend() {

        // Check if there are enough entries for us to measure first
        if (entries.size() >= 2) {
            // Initialize variables representing the dates and measurements
            LocalDate newestDate = entries.lastKey();
            LocalDate oneWeekAgo = newestDate.minusWeeks(1);
            Float newestMeasure = entries.get(newestDate);
            Float lastMeasure = null;

            // BOOLEAN to represent whether we can make a weight prediction
            boolean predictionMade = true;

            // check if a weight entry exists about one week ago
            // check exactly one week ago
            if (entries.containsKey(oneWeekAgo)) {
                lastMeasure = entries.get(oneWeekAgo);
            } // check the day before one week ago
            else if (entries.containsKey(oneWeekAgo.minusDays(1))) {
                lastMeasure = entries.get(oneWeekAgo.minusDays(1));
            } // check the day after one week ago
            else if (entries.containsKey(oneWeekAgo.plusDays(1))) {
                lastMeasure = entries.get(oneWeekAgo.plusDays(1));
            } else {
                // in this case, we could not find proper data to make a prediction, and will let the user know accordingly
                predictionMade = false;
            }

            // if we can make a weight prediction, then calculate
            if (predictionMade) {
                // Calculate the average rate of daily weight change using the client's
                // last measured weight and newest weight and the days between these measurements
                float measurementChange = newestMeasure - lastMeasure;
                long daysBetween = ChronoUnit.DAYS.between(oneWeekAgo, newestDate);
                float avgDailyChange = (measurementChange / daysBetween);

                // Calculate the client's predicted weight in the next 30 days by adding their current weight
                // and the average rate of daily weight change multiplied by 30 days
                // return the client's predicted weight in the next 30 days
                return (Math.round((newestMeasure + (avgDailyChange * 30)) * 10f) / 10f);

                // If we can't make a prediction, return -1
            } else {
                return -1;
            }
        }
        // If there are not enough entries, then return -1
        else {
            return -1;
        }
    }

    /**
     * Prints message representing data trend from calculate Trend
     * This function does not return anything, but prints the client's expected trend change within the next 30 days
     */
    public void printTrend() {
        float x = calculateTrend();
        if (x == -1) {
            System.out.println("There was not enough data to predict the client's " + measurementEnum.getString() + " within the next 30 days.");
        }
        else {
            System.out.println("The client's " + measurementEnum.getString() + " will be " + x + ".");
        }
    }

     /**
     * Creates a String from the dates and entries
     * @return String representing the data in the entry treemap
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        // append the name of the Measurement object to the beginning of the line
        //stringBuilder.append(measurementEnum.getString()).append(":\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        // loop the list of entries and append their string to the line
        for (Map.Entry<LocalDate, Float> measurements : entries.entrySet()) {
            String formattedDate = measurements.getKey().format(formatter);
            Float value = measurements.getValue();
            stringBuilder.append("  ").append(formattedDate).append(" -> ").append(value).append("\n");
        }

        return stringBuilder.toString();
    }

}
