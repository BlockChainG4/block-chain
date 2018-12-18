package com.g4.blockchain.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.BlockChain;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.*;

@Service // filewriter in test know about th9
public class FileWriter {
    // Creates file locally
    //

    @Inject
    private ObjectMapper mapper;

    public void save(BlockChain chain, String fileName) throws Exception {
        /*PrintWriter pw = new PrintWriter(new FileOutputStream("blockchain.txt"));
        for (String hash : blockArray)
            pw.println(hash);
        pw.close();*/

        // Make sure you use \\ instead of \
        Writer bufferedWriter = null;

        try {

            //Creating a file
            Writer fileWriter = new java.io.FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);

            String chainJson = mapper.writeValueAsString(chain);
            // Writing the content
            bufferedWriter.write(chainJson);
        } catch (IOException e) {
            System.out.println("Problem occurs when creating file " + fileName);
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
    public BlockChain readFileInList(String fileName) throws IOException {

        if (!Files.exists(Paths.get(fileName))) {
            Files.createFile(Paths.get(fileName));
        }

        try
        {
            String chainJSON = Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
            if (!chainJSON.isEmpty()) {
                return mapper.readValue(chainJSON, BlockChain.class);
            }
        }

        catch (IOException e)
        {

            // do something
            e.printStackTrace();
        }

        return new BlockChain();
    }

}
