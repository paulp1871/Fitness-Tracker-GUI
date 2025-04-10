package com.example.demo03.tracker;

import com.example.demo03.enums.MeasurementEnum;

import java.time.LocalDate;

public class Calories extends Measurement {
    public Calories() {
        super(MeasurementEnum.CALORIES);
    }

    /**
     * This function will average the calorie intake from the latest entry (ideally today if possible) to one week ago
     * Overrides the calculateTrend in Measurement
     */
    @Override
    public float calculateTrend () {

        // handle empty entries
        if (entries.isEmpty()) {
            return -1;
        }

        LocalDate date = null;

        // Check if there is a key representing today, if not, obtain the latest date (last key) in the entries key set
        // TODO: Abdullah's note: just use .lastKey(), it will get you today if that exists since today is the latest
        if (entries.containsKey(LocalDate.now())) {
            date = LocalDate.now();
        } else {
            date = entries.lastKey();
        }
        
        // initialize app.tracker variables
        float totalCalories = 0;
        int daysWithEntries = 0;

        // loop through each day in the past week, checking if entries exist (in which case we add up calorie intake and days with entries
        for (int i = 0; i < 7; i++) {
            // check that a weight entry exists for the given date
            if (entries.containsKey(date.minusDays(i))) {
                totalCalories += entries.get(date.minusDays(i));
                daysWithEntries++;
            }
        }

        // we now have the total calorie intake for the week, as well as the amount of days in the week that actually have entries

        // check that there were entries in the first place
        if (daysWithEntries == 0) {
            // in this case, there were no entries for the week
            return -1;
        } else {
            float average = totalCalories / daysWithEntries;
            return Math.round(average);
        }
    }

    /**
     * Prints the calorie intake calculated in calculateTrend
     * Overrides printTrend in Measurement
     */
    @Override
    public void printTrend() {
        float average = calculateTrend();
        if (average == -1) {
            System.out.println("Not enough data to calculate average calorie intake for this week.");
        }
        else {
            System.out.println("Average calorie intake for this week was " + average + ".");
        }
   }
}
