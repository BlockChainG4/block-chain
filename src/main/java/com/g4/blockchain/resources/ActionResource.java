package com.g4.blockchain.resources;

import com.g4.blockchain.BlockChain;
import com.g4.blockchain.Transaction;
import com.g4.blockchain.services.BlockChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("action")
@Produces(MediaType.APPLICATION_JSON)
public class ActionResource {

    @Autowired
    private BlockChainService service;


    @PostMapping(path= "transaction")
    public BlockChain addTransaction(@RequestBody Transaction trans) throws IOException, ScriptException {
        return service.addTransaction(trans);
//        return Collections.singletonMap("message", String.format("Transaction will be added to Block {%d}", index));
    }


    @PostMapping(path = "mine")
    public BlockChain mine() throws IOException {
        return service.mine();
    }
}
