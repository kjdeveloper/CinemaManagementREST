package com.app.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Transactional
public interface FileService {

    public static void saveToFile(String fileName, String message) {
        try {
            FileWriter writer = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(writer);

            printWriter.println(message);
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
