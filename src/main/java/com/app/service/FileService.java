package com.app.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Transactional
public interface FileService {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static void saveToFile(String filename, Object map) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(gson.toJson(map));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
