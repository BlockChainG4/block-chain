package com.g4.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.services.BlockChainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BlockChainTest {

    @Inject
    private ObjectMapper mapper;

    @Test
    public void testValidChain() throws JsonProcessingException {
        BlockChain blockchain = new BlockChain();
        int difficulty = 4;

        LinkedList<Transaction> transactionList = new LinkedList<>();
        blockchain.add(new Block("0", transactionList));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        transactionList.add(new Transaction("1+2"));
        blockchain.add(new Block(blockchain.get(blockchain.size()-1).getHash(), transactionList));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        transactionList.add(new Transaction("4*5"));
        blockchain.add(new Block(blockchain.get(blockchain.size()-1).getHash(), transactionList));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + blockchain.isChainValid(2));

        String blockchainJson = mapper.writeValueAsString(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }
}
