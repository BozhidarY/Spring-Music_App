package com.example.demo.Databases.ConsoleFIleHandling;

import com.example.demo.Entities.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GsonProvider implements LoadSaveProvider{
    private static final Logger logger = (Logger) LogManager.getLogger(GsonProvider.class);
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Users.class, new UserDeserializer())
            .setPrettyPrinting()
            .create();
    Scanner scanner = new Scanner(System.in);

    public <T> T loadObject(String filePath, Class<T> tClass) {
        try (FileReader fileReader = new FileReader(filePath)) {
//            Type typeToken = new TypeToken<Class<T>>() {}.getType();
            logger.info("Users have beed loaded from {}", filePath);
            return gson.fromJson(fileReader, tClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error("Failed to load from file {}", filePath);
            System.out.println("THe filePath you provided is wrong. Fix it");
            filePath = scanner.nextLine();
            return loadObject(filePath,tClass);

        }
    }

    public <T> void saveObject(String filePath, T object) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(object, fileWriter);
        } catch (IOException e) {
            logger.error("Failed to save in file {}", filePath);
            System.out.println("THe filePath you provided is wrong. Fix it");
            filePath = scanner.nextLine();
            saveObject(filePath, object);
        }
    }
}
