package com.portfolioproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.portfolioproject.model.User;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class JsonUtil {

    private static final String FILE_NAME = "portfolio.json";

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }


    // Save users to JSON file
    public static void saveUsers(Collection<User> users) {

        try {

            mapper.writeValue(new File(FILE_NAME), users);

            System.out.println("Data saved to portfolio.json");

        } catch (IOException e) {

            System.out.println("Error while saving data.");
            e.printStackTrace();
        }
    }


    // Read users from JSON file
    public static User[] loadUsers() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {

                System.out.println("portfolio.json not found.");

                return new User[0];
            }

            return mapper.readValue(file, User[].class);

        } catch (IOException e) {

            System.out.println("Error while reading JSON file.");
            e.printStackTrace();

            return new User[0];
        }
    }
}