package com.g4.blockchain;

import com.g4.blockchain.utilities.FileWriter;
import org.junit.Test;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileWriterTest {

    @Inject
    FileWriter fileWriter;

    @Test
    public void writerTest() throws Exception {
        List<String> supplierNames = new ArrayList<String>();
        supplierNames.add("sup1");
        supplierNames.add("sup2");
        supplierNames.add("sup3");
        fileWriter.save(supplierNames);
    }
}
