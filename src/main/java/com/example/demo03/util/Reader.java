package com.example.demo03.util;

import com.example.demo03.enums.MeasurementEnum;
import com.example.demo03.tracker.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Reader {

    /**
     * Reads database.txt to get all the client data
     * @return clientList
     */
    public static HashMap<String, Client> loadFile() {

        // initialize output of clientList
        HashMap<String, Client> cl = new HashMap<>();

        try {

            // initialize fileReader
            File f = new File("database.txt");
            if (!f.exists()) {
                return null;
            }

            FileReader fr = new FileReader(f);
            BufferedReader r = new BufferedReader(fr);

            // initialize this variable so it doesn't reset each loop
            String line;

            // loop line-by-line
            while (r.ready()) {

                // read the line
                line = r.readLine();

                // check if we moved on to a new client
                if (line.equals("CLIENT")) {

                    // get client name
                    String name = r.readLine();

                    // get client height
                    String heightStr = r.readLine();
                    int height = Integer.parseInt(heightStr);

                    // initialize new client
                    Client c = new Client(name, height);

                    // loop through measurements
                    // using a for-loop because im an anarchist (i only care about looping the correct amount of times)
                    for (MeasurementEnum i : MeasurementEnum.values()) {

                        // parse measurementEnum
                        String measureName = r.readLine();
                        MeasurementEnum measureEnum = MeasurementEnum.getEnumByName(measureName);

                        // read the entries
                        String entriesLine = r.readLine();

                        if (!entriesLine.isEmpty()) {
                            // separate the entries by each entry
                            String[] entriesStr = entriesLine.split(",");

                            // for each entry,
                            for (String entryStr : entriesStr) {

                                // separate by date and value
                                String[] entryData = entryStr.split(":");

                                // parse date
                                String dateStr = entryData[0];
                                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yy"));

                                // parse value
                                String valueStr = entryData[1];
                                Float value = Float.parseFloat(valueStr);

                                // add them to client data
                                c.getMeasurement(measureEnum).addEntry(date, value);

                            }
                        }

                    }


                    // put the client in the list
                    cl.put(name, c);

                }

            }

            // close reader
            r.close();
            fr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // return clientList
        return cl;

    }
}
