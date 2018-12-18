package com.g4.blockchain;

import com.g4.blockchain.utilities.FileWriter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
public class FileWriterTest {

    @Test
    public void writerTest() throws Exception {
        List<String> supplierNames = new ArrayList<String>();
        supplierNames.add("sup1");
        supplierNames.add("sup2");
        supplierNames.add("sup3");
        FileWriter.save(supplierNames, "FileMaker.txt");

        List l = FileWriter.readFileInList("FileMaker.txt");

        Assert.assertEquals(supplierNames.size(), l.size());
        Assert.assertArrayEquals(supplierNames.toArray(), l.toArray());

    }
}
