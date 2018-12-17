package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BlockChainTest {

    @Inject
    private ObjectMapper mapper;

    @Test
    public void testValidChain() throws JsonProcessingException {
        BlockChain blockchain = new BlockChain();

        Block b = new Block("0");
        blockchain.add(b);
        blockchain.addTransaction(new Transaction("2+1"));
        blockchain.addTransaction(new Transaction("3*3"));
        blockchain.addTransaction(new Transaction("4*3"));
        blockchain.addTransaction(new Transaction("5*3"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(1).mineBlock(BlockChain.DIFFICULTY);
        blockchain.get(0).mineBlock(BlockChain.DIFFICULTY);

        System.out.println("\nBlockchain is Valid: " + blockchain.isChainValid(BlockChain.DIFFICULTY));

        String blockchainJson = mapper.writeValueAsString(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }
}
