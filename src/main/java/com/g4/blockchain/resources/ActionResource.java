package com.g4.blockchain.resources;

import com.g4.blockchain.Block;
import com.g4.blockchain.BlockChain;
import com.g4.blockchain.Peer;
import com.g4.blockchain.Transaction;
import com.g4.blockchain.dto.BlockChainResponseDto;
import com.g4.blockchain.services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("action")
@Produces(MediaType.APPLICATION_JSON)
public class ActionResource {

    @Autowired
    BlockChainService service;

    @PostMapping(path= "transaction")
    public Map<String, String> addTransaction(@RequestBody Transaction trans) {
        int index = service.addTransaction(trans);
        return Collections.singletonMap("message", String.format("Transaction will be added to Block {%d}", index));
    }

    @GetMapping(path = "mine")
    public Map<String, Object> mine() {
        Block lastBlock = service.getLatestBlock();
        long lastProof = lastBlock.getProof();
        long proof = service.proofOfWork(lastProof);
        service.addTransaction(new Transaction("0", "NodeId", BigDecimal.valueOf(1)));
        Block block = service.createBlock(new Block(proof, null));
        Map<String, Object> response = new HashMap<>();
        response.put("message", "New Block Forged");
        response.put("index", block.getIndex());
        response.put("transactions", block.getTransactions());
        response.put("proof", block.getProof());
        response.put("previous_hash", block.getPreviousHash());
        return response;
    }

    @GetMapping(path = "blockchain")
    public BlockChainResponseDto getBlockChain() {
        return new BlockChainResponseDto(service.getBlockChain());
    }

}
