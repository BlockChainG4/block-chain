package com.g4.blockchain.utilities;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service // filewriter in test know about th9
public class FileWriter {
    // Creates file locally
    public static void save(List<String> blockArray) throws Exception {
        /*PrintWriter pw = new PrintWriter(new FileOutputStream("blockchain.txt"));
        for (String hash : blockArray)
            pw.println(hash);
        pw.close();*/

        // Make sure you use \\ instead of \
        String filePath = "FileMaker.txt";
        Writer bufferedWriter = null;

        try {

            //Creating a file
            Writer fileWriter = new java.io.FileWriter(filePath);
            bufferedWriter = new BufferedWriter(fileWriter);

            // Writing the content
            for (String line : blockArray) {
                bufferedWriter.write(line);
                bufferedWriter.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            System.out.println("Problem occurs when creating file " + filePath);
            e.printStackTrace();
        } finally {

            //Closing the file
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    System.out.println("Problem occurs when closing file !");
                    e.printStackTrace();
                }
            }
        }
    }
}
