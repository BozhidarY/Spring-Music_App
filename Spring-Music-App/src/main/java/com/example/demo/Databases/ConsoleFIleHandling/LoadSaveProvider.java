package com.example.demo.Databases.ConsoleFIleHandling;

import java.io.IOException;
import java.lang.reflect.Type;

public interface LoadSaveProvider {

    <T> void saveObject(String fileName, T object) throws IOException;
    <T> T loadObject(String fileName, Class<T> tClass) throws IOException;
}
