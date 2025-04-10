package com.example.demo03;

import com.example.demo03.enums.*;
import com.example.demo03.tracker.*;
import com.example.demo03.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.demo03.util.Reader;
import org.junit.jupiter.api.Test;

/**
 * CPSC 233 W25 Project - Fitness Tracker
 * @author Vini Godoy de Melo, Abdullah Kabani, Paul Pham
 * @date 02/03/25
 * @tutorial T03
 * @email vini.godoydemelo@ucalgary.ca, abdullah.kabbani@ucalgary.ca
 *
 * Contains the unit tests for the fitness app.tracker
 */

// TODO: fix all the tests

// testing initClientDataSet
public class UnitTests {

    // testing calculateTrend for no entries
    @Test
    void calculateTrend_ZeroEntries() {

        Measurement Bicep = new Measurement(MeasurementEnum.BICEP);

        float expected = Bicep.calculateTrend();
        // Compare the expected output to the actual output
        float actual = -1;

        assertEquals(expected, actual);
    }

    // Created test for three entries but with a valid entry dating to a week ago
    @Test
    void calculateTrend_ThreeEntriesWithEntryAWeekAgo() {

        Measurement Bicep = new Measurement(MeasurementEnum.BICEP);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Initialize the values
        String oldDateStr = "01/01/2024";
        LocalDate oldDate = LocalDate.parse(oldDateStr, formatter);
        Float oldWeight = 87.5F;

        String newerDateStr = "31/01/2024";
        LocalDate newerDate = LocalDate.parse(newerDateStr, formatter);
        Float newerWeight = 77.5F;

        String newestDateStr = "07/02/2024";
        LocalDate newestDate = LocalDate.parse(newestDateStr, formatter);
        Float newestWeight = 75.2F;


        Bicep.addEntry(oldDate, oldWeight);
        Bicep.addEntry(newerDate, newerWeight);
        Bicep.addEntry(newestDate, newestWeight);

        // Compare the expected output to the actual output
        Float expected = 65.3F;
        Float actual = Bicep.calculateTrend();
        assertEquals(expected, actual);
    }

    //-----Testing--Abdullah's--Functions-----//

    /*
     * Testing getCurrentBMI
     *
     * weight is assumed to be a float greater than zero, assumed to be up to 2 decimal places
     * height is assumed to be a float greater than zero, assumed to be up to 1 decimal place
     * returns (weight)/(height squared)
     */

    // basic test
    @Test
    void getCurrentBMITest() {
        // weight = 100 kg
        // height = 180 cm
        float expected = 30.9F;
        float actual = Client.getCurrentBMI(100, 180);
        assertEquals(expected, actual);
    }

    // min weight min height
    @Test
    void getCurrentBMITest1() {
        // weight = 0.01 kg
        // height = 0.01 cm
        float expected = 10000.0F;
        float actual = Client.getCurrentBMI(0.01F, 0.1F);
        assertEquals(expected, actual);
    }

    // weight higher than height
    @Test
    void getCurrentBMITest2() {
        // weight = 200 kg
        // height = 150 cm
        float expected = 88.9F;
        float actual = Client.getCurrentBMI(200, 150);
        assertEquals(expected, actual);
    }

    /*
     * Testing roundFloat
     *
     * num is assumed to be any float, with no limitations
     * precision is assumed to be any non-negative integer
     */

    // basic test
    @Test
    void roundFloatTest() {
        // num = 3.14159
        // precision = 2
        float expected = 3.14F;
        float actual = Client.roundFloat(3.14159F, 2);
        assertEquals(expected, actual);
    }

    // zero precision
    @Test
    void roundFloatTest1() {
        float expected = 3;
        float actual = Client.roundFloat(3.14159F, 0);
        assertEquals(expected, actual);
    }

    // more precision than specified digits
    @Test
    void roundFloatTest2() {
        float expected = 3.14159F;
        float actual = Client.roundFloat(3.14159F, 10);
        assertEquals(expected, actual);
    }

    // num is zero
    @Test
    void roundFloatTest3() {
        float expected = 0;
        float actual = Client.roundFloat(0.000000000F, 4);
    }

    // num is very low decimal
    @Test
    void roundFloatTest4() {
        float expected = 0;
        float actual = Client.roundFloat(0.00000001F, 4);
    }

    // num is negative
    @Test
    void roundFloatTest5() {
        float expected = -3.14F;
        float actual = Client.roundFloat(-3.14159F, 2);
        assertEquals(expected, actual);
    }

    /*
     * Testing predictFutureBMI
     *
     * weightEntries is assumed to have one or more entries
     * the keys are assumed to be valid dates
     * the values are assumed to be floats larger than zero, with up to 2 decimal places of precision
     *
     * height is assumed to be a float larger than zero with up to one decimal place of precision
     */

    // basic test
    @Test
    void predictFutureBMITest() {
        Client c = new Client("c", 180);
        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 100F);
        weightEntries.put(LocalDate.of(2025, 1, 8), 90F);

        float actual = c.predictFutureBMI(weightEntries, 180);
        float expected = 24.7F;
        assertEquals(expected, actual);
    }

    // value exists for day before one week ago
    @Test
    void predictFutureBMITest1() {
        Client c = new Client("c", 180);
        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 100F);
        weightEntries.put(LocalDate.of(2025, 1, 9), 90F);

        float actual = c.predictFutureBMI(weightEntries, 180);
        float expected = 24.7F;
        assertEquals(expected, actual);
    }

    // value exists for day after one week ago
    @Test
    void predictFutureBMITest2() {
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 100F);
        weightEntries.put(LocalDate.of(2025, 1, 7), 90F);

        float actual = c.predictFutureBMI(weightEntries, 180);
        float expected = 24.7F;
        assertEquals(expected, actual);
    }

    // BMI does not change
    @Test
    void predictFutureBMITest3() {
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 100F);
        weightEntries.put(LocalDate.of(2025, 1, 8), 100F);

        float actual = c.predictFutureBMI(weightEntries, 180);
        float expected = 30.9F;
        assertEquals(expected, actual);
    }

    // BMI increases
    @Test
    void predictFutureBMITest4() {
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 90F);
        weightEntries.put(LocalDate.of(2025, 1, 8), 100F);

        float actual = c.predictFutureBMI(weightEntries, 180);
        float expected = 34F;
        assertEquals(expected, actual);
    }

    /*
     * Testing calculateBMIDifference
     *
     * WeightEntries is assumed to have at least one entry (with valid dates and valid weight measurements)
     * HeightEntries is assumed to have at least one entry (with valid dates and valid height measurements)
     *
     * date pattern dd/MM/yy
     *
     * arg 0 contains the date of the first weight entry
     * arg 1 contains the date of the last weight entry
     * arg 2 contains the first BMI
     * arg 3 contains the last BMI
     *
     * really all the function does is store data from the TreeMaps into a string array
     */

    // basic test
    @Test
    void calculateBMIDifferenceTest1() {
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();


        weightEntries.put(LocalDate.of(2025, 1, 1), 90F);


        weightEntries.put(LocalDate.of(2025, 1, 8), 100F);

        String[] expected = {
                "01/01/25",
                "08/01/25",
                "27.8",
                "30.9"
        };

        String[] actual = c.calculateBMIDifference(weightEntries, c.getHeight());

        assertArrayEquals(expected, actual);
    }

    // only one weight entry
    @Test
    void calculateBMIDifferenceTest2() {
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();

        weightEntries.put(LocalDate.of(2025, 1, 1), 90F);


        String[] expected = {
                "01/01/25",
                "01/01/25",
                "27.8",
                "27.8"
        };

        String[] actual = c.calculateBMIDifference(weightEntries, c.getHeight());

        assertArrayEquals(expected, actual);
    }

    // multiple weight entries
    @Test
    void calculateBMIDifferenceTest3() {
        // init client
        Client c = new Client("c", 180);

        TreeMap<LocalDate, Float> weightEntries = c.getMeasurement(MeasurementEnum.WEIGHT).getEntries();
        int height = c.getHeight();

        weightEntries.put(LocalDate.of(2025, 1, 1), 90F);
        weightEntries.put(LocalDate.of(2025, 1, 8), 93F);
        weightEntries.put(LocalDate.of(2025, 1, 12), 87F);
        weightEntries.put(LocalDate.of(2025, 1, 20), 83F);

        String[] expected = {
                "01/01/25",
                "20/01/25",
                "27.8",
                "25.6"
        };

        String[] actual = c.calculateBMIDifference(weightEntries, height);

        assertArrayEquals(expected, actual);
    }

    /*
     * Testing calculateTrend (calorie class override)
     * This method gets this weeks average calorie intake which overrides calculateTrend from class Measurement
     *
     */

    // basic test
    @Test
    void getWeeklyCalorieTestCalculateTrend() {
        Calories calories = new Calories();
        calories.addEntry(LocalDate.of(2025, 1, 1), 2000F);
        calories.addEntry(LocalDate.of(2025, 1, 2), 1900F);
        calories.addEntry(LocalDate.of(2025, 1, 3), 2100F);
        calories.addEntry(LocalDate.of(2025, 1, 4), 1950F);
        calories.addEntry(LocalDate.of(2025, 1, 5), 2050F);
        calories.addEntry(LocalDate.of(2025, 1, 6), 2100F);
        calories.addEntry(LocalDate.of(2025, 1, 7), 1900F);

        float actual = calories.calculateTrend();
        float expected = 2000F;
        assertEquals(expected, actual);
    }

    // no entries
    @Test
    void getWeeklyCalorieTest1CalculateTrend() {
        Calories calories = new Calories();
        float actual = calories.calculateTrend();
        float expected = -1F;
        assertEquals(expected, actual);
    }

    // one entry
    @Test
    void getWeeklyCalorieTest2CalculateTrend() {
        Calories calories = new Calories();
        calories.addEntry(LocalDate.of(2025, 1, 7), 2000F);

        float actual = calories.calculateTrend();
        float expected = 2000F;
        assertEquals(expected, actual);
    }

    // eight entries (it should only take the 7 from the week)
    @Test
    void getWeeklyCalorieTest3CalculateTrend() {
        Calories calories = new Calories();
        calories.addEntry(LocalDate.of(2025, 1, 1), 0F);
        calories.addEntry(LocalDate.of(2025, 1, 2), 1900F);
        calories.addEntry(LocalDate.of(2025, 1, 3), 2100F);
        calories.addEntry(LocalDate.of(2025, 1, 4), 1950F);
        calories.addEntry(LocalDate.of(2025, 1, 5), 2050F);
        calories.addEntry(LocalDate.of(2025, 1, 6), 2100F);
        calories.addEntry(LocalDate.of(2025, 1, 7), 1900F);
        calories.addEntry(LocalDate.of(2025, 1, 8), 2000F);

        float actual = calories.calculateTrend();
        float expected = 2000F;
        assertEquals(expected, actual);
    }

    /*
     * Testing app.util.Logger.saveData(...)
     *
     * db is a hashmap of String keys and Client values.
     *           It is expected to be a valid clientList, possibly entirely null,
     *           or possibly having some measurement TreeMaps be null.
     * void, but it will write to a file called 'database.txt' in the proper format
     */

    // basic test
    @Test
    void saveDataTest() {
        // set up expected
        String[] expected = {
                "CLIENT",
                "John",
                "180",
                "WEIGHT",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "BICEP",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "WAIST",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "CHEST",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "THIGHS",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "CALORIES",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "CLIENT",
                "Bobby",
                "160",
                "WEIGHT",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
                "BICEP",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "WAIST",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
                "CHEST",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "THIGHS",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "CALORIES",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
        };

        // set up actual
        // create clientList to save
        HashMap<String, Client> clientList = new HashMap<>();
        Client john = new Client("John", 180);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 12), 92F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 12), 92F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 12), 92F);
        Client bobby = new Client("Bobby", 160);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 12), 92F);
        clientList.put("John", john);
        clientList.put("Bobby", bobby);
        // write data to save file
        Logger.saveData(clientList);
        // read back the file
        ArrayList<String> actualBuffer = new ArrayList<>();
        try {
            File file = new File("database.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                actualBuffer.add(br.readLine());
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] actual = actualBuffer.toArray(new String[0]);

        // compare
        assertArrayEquals(expected, actual);
    }

    // test no entries
    @Test
    void saveDataTestEmpty() {

        // set up expected
        String expected = null;

        // set up actual
        HashMap<String, Client> cl = new HashMap<>();
        Logger.saveData(cl);
        // read back file
        String actual = "IM NOT INITIALIZED YET!!!!!!";
        try {
            File file = new File("database.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            actual = br.readLine();
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // compare
        assertEquals(expected, actual);
    }

    // test no measurements
    @Test
    void saveDataTestNoMeasurements() {
        // set up expected
        String[] expected = {
             "CLIENT",
             "John",
             "180",
             "WEIGHT",
             "",
             "BICEP",
             "",
             "WAIST",
             "",
             "CHEST",
             "",
             "THIGHS",
             "",
             "CALORIES",
             ""
        };

        // set up actual
        HashMap<String, Client> cl = new HashMap<>();
        Client john = new Client("John", 180);
        cl.put("John", john);
        Logger.saveData(cl);
        // read back the file
        ArrayList<String> actualBuffer = new ArrayList<>();
        try {
            File f = new File("database.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while(br.ready()) {
                actualBuffer.add(br.readLine());
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] actual = actualBuffer.toArray(new String[0]);

        // compare
        assertArrayEquals(expected, actual);
    }

    /*
     * Testing file reader
     */

    // basic test
    @Test
    void loadFileTest() {
        // set up expected
        HashMap<String, Client> expected = new HashMap<>();
        Client john = new Client("John", 180);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2025, 10, 12), 92F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2025, 10, 12), 92F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 10), 90F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 11), 91F);
        john.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2025, 11, 12), 92F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 10), 90F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 11), 91F);
        john.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2025, 10, 12), 92F);
        Client bobby = new Client("Bobby", 160);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.WEIGHT).addEntry(LocalDate.of(2024, 10, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.BICEP).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.WAIST).addEntry(LocalDate.of(2024, 10, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.CHEST).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.THIGHS).addEntry(LocalDate.of(2024, 11, 12), 92F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 10), 90F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 11), 91F);
        bobby.getMeasurement(MeasurementEnum.CALORIES).addEntry(LocalDate.of(2024, 10, 12), 92F);
        expected.put("John", john);
        expected.put("Bobby", bobby);

        // set up actual
        String[] saveStr = {
                "CLIENT",
                "John",
                "180",
                "WEIGHT",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "BICEP",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "WAIST",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "CHEST",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "THIGHS",
                "10/11/25:90.0,11/11/25:91.0,12/11/25:92.0",
                "CALORIES",
                "10/10/25:90.0,11/10/25:91.0,12/10/25:92.0",
                "CLIENT",
                "Bobby",
                "160",
                "WEIGHT",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
                "BICEP",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "WAIST",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
                "CHEST",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "THIGHS",
                "10/11/24:90.0,11/11/24:91.0,12/11/24:92.0",
                "CALORIES",
                "10/10/24:90.0,11/10/24:91.0,12/10/24:92.0",
        };
        try {
            File file = new File("database.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String str : saveStr) {
                bw.write(str);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Client> actual = Reader.loadFile();

        // compare
        assertEquals(expected.toString(), actual.toString());
    }

    // test empty file
    @Test
    void loadFileTestEmpty() {
        // set up expected
        HashMap<String, Client> expected = new HashMap<>();
        // set up actual
        try {
            File file = new File("database.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Client> actual = Reader.loadFile();
        // compare
        assertEquals(expected.toString(), actual.toString());
    }

    // test client with no measurements
    @Test
    void loadFileTestNoMeasurements() {
        // set up expected
        HashMap<String, Client> expected = new HashMap<>();
        Client john = new Client("John", 160);
        expected.put("John", john);
        // set up actual
        try {
            File file = new File("database.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            String[] arr = {
                    "CLIENT",
                    "John",
                    "180",
                    "WEIGHT",
                    "",
                    "BICEP",
                    "",
                    "WAIST",
                    "",
                    "CHEST",
                    "",
                    "THIGHS",
                    "",
                    "CALORIES",
                    ""
            };
            for (String str : arr) {
                bw.write(str);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, Client> actual = Reader.loadFile();
        // compare
        assertEquals(expected.toString(), actual.toString());
    }

}
