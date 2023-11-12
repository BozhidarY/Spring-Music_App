package com.example.demo.Databases.ConsoleFIleHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JacksonProvider implements LoadSaveProvider {

    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public <T> T loadObject(String filePath, Class<T> tClass)  {
        try {
            return objectMapper.readValue(new File(filePath),tClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error("Failed to load songs from file {}", filePath);
            filePath = scanner.nextLine();
            return loadObject(filePath, tClass);
        }
    }
    public <T> void saveObject(String filePath, T object) {
        try {
            objectMapper.writeValue(new File(filePath), object);
        } catch (IOException e) {
//            logger.error("Failed to save in file {}", filePath);
            filePath = scanner.nextLine();
            saveObject(filePath, object);
        }
    }

}
