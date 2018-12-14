package com.g4.blockchain.resources;

import com.g4.blockchain.BlockChain;
import com.g4.blockchain.Peer;
import com.g4.blockchain.Transaction;
import com.g4.blockchain.services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("action")
@Produces(MediaType.APPLICATION_JSON)
public class ActionResource {

    @Autowired
    BlockChainService service;

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction trans) {
        service.addTransaction(trans);
        return null;
    }

    @GetMapping(path = "mine")
    public String mine() {
        //TO DO
        return "MINE BLOCK";
    }

    @GetMapping(path = "calculate")
    public String calculate() {
        //TO DO
        return "CALCULATING";
    }

    @GetMapping(path = "blockchain")
    public BlockChain getBlockChain() {
        //TO DO
        return null;
    }

}
