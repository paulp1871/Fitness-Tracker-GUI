package com.example.demo03.util;

import com.example.demo03.enums.MeasurementEnum;
import com.example.demo03.tracker.Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TreeMap;

public class Logger {

    /**
     * Writes all data to a text file to save for later
     * @param db the ClientList containing all the data
     */
    public static void saveData(HashMap<String, Client> db, File f) {
        try {

            // initialize dateTimeFormatter for later use
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

            // create fileWriter
            f.createNewFile(); // creates new file if not existing already
            FileWriter fw = new FileWriter(f);
            BufferedWriter w = new BufferedWriter(fw);

            // loop through each client
            for (Client c : db.values()) {
                w.write("CLIENT");
                w.newLine();

                // client name and height
                w.write(c.getName());
                w.newLine();
                w.write(Integer.toString( c.getHeight() ));
                w.newLine();

                // loop through each measurement
                for (MeasurementEnum m : MeasurementEnum.values()) {

                    // write the measurement name
                    w.write( m.toString() );
                    w.newLine();

                    TreeMap<LocalDate, Float> entries = c.getMeasurement(m).getEntries();

                    // loop through the tree map
                    for (LocalDate date : entries.keySet()) {

                        // add date:value entries to the string
                        w.write(date.format(formatter));
                        w.write(":");
                        w.write(Float.toString( entries.get(date) ));

                        // don't add comma if it is the last entry
                        if (date != entries.lastKey()) {
                            w.write(",");
                        }

                    }


                    // move on to the next client
                    w.newLine();

                }
            }


            // close the file writer
            w.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*
 * SAVE FILE FORMAT
 *
 * In general (line separated),
 * ATTRIBUTE_NAME
 * ATTRIBUTE_VALUE
 *
 * file format
 *
 * for each client,
 * CLIENT
 * {clientName}
 * {height (cm)}
 * {measurementEnumName}
 * {date}:{value},{date}:{value},... (measurement entries are divided by commas, for each entry, date and value are divided by colon
 * ... (repeats for each measurement)
 * ... (repeats for each client)
 *
 */