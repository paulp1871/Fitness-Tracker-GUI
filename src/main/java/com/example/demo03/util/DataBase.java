package com.example.demo03.util;

import com.example.demo03.tracker.Client;

import java.util.*;


public class DataBase {

    // Store all the Client objects
    private static final HashMap<String, Client> clientList = new HashMap<>();

    /**
     * Inserts a Client object into the clientList
     * @param name the name of the client we're adding to the list
     * @param height the client's height
     */
    public static void addClient(String name, int height) {
        clientList.put(name, new Client(name, height));
    }

    public static Client getClient(String name) {
        return clientList.get(name);
    }

    public static boolean isExistingClient(String name) {
        return clientList.containsKey(name);
    }

    // Compare heights of clients using comparators
    public static class HeightComparison implements Comparator<Client> {
        @Override
        public int compare(Client c1, Client c2) {
            int c1Height = c1.getHeight();
            int c2Height = c2.getHeight();

            return Double.compare(c2Height, c1Height);
        }
    }

    /**
     * Compares client by height and prints them out
     *
     * @return
     */
    public static String sortClientsByHeight() {

        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("Clients listed from tallest to shortest:");

        // Get list of client's heights and sort
        List<Client> clients = new ArrayList<>(clientList.values());
        clients.sort(new HeightComparison());

        // Print out their heights
        for (Client c: clients) {
           String clientName = c.getName();
           int clientHeight = c.getHeight();
           stringBuilder.append("Client: ").append(clientName).append(" | ").append(clientHeight).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Prints a list with all the client names
     * clientList: a HashMap containing all the clients (keys) and all their respective data set
     */
    public static String printAllClientNames() {

        // print the keys (client names) in the client list
        return String.valueOf(clientList.keySet());
    }

    /**
     * Overwrites clientList with clientList read from savefile
     */
    public static void loadSave() {
        HashMap<String, Client> save = Reader.loadFile();
        if (save != null) {
            clientList.clear(); // remove all entries
            for (String name : save.keySet()) {
                clientList.put(name, save.get(name));
            }
        } else {
            System.out.println("No save data found");
        }
    }

    /**
     * writes save data to file
     */
    public static void logSave() {
        Logger.saveData(clientList);
    }

    /**
     * This function builds a database String by looping the list of clients, and getting each client's String
     * @return String containing the data of all the clients and their names
     */
    @Override
    public String toString() {


        StringBuilder stringBuilder = new StringBuilder();

        for (String client : clientList.keySet()) {
            stringBuilder.append(client);
        }

        return stringBuilder.toString();
    }

}
