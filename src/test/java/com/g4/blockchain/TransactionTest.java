package com.g4.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TransactionTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    public void transactionTest() throws IOException {
        Transaction t1 = new Transaction();
        t1.setOperation("1+1");
        t1.setResult("2");

        Transaction t2 = mapper.readValue(t1.toString(), Transaction.class);
        Assert.assertNotNull(t2);
        Assert.assertEquals(t1.toString(), t2.toString());
    }
}
