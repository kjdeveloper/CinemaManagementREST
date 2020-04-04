package com.app.service.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;


@Transactional
public interface FileService {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static void saveToFile(String filename, Object data) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(gson.toJson(data));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveToPDFFile(String filename, Object data) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(gson.toJson(data), font);

            document.add(chunk);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
