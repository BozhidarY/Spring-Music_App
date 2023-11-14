package com.example.demo.Databases.ConsoleFIleHandling;

public class LibraryProviderFactory {
    public static LoadSaveProvider createLibraryProvider(String dataLibraryChoice) {
        if ("gson".equals(dataLibraryChoice)) {
            return new GsonProvider();
        } else if ("jackson".equals(dataLibraryChoice)) {
            return new JacksonProvider();
        } else {
            throw new IllegalArgumentException("Incorrect info");
        }
    }
}
