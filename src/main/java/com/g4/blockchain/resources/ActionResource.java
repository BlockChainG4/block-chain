package com.g4.blockchain.resources;

import com.g4.blockchain.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@RestController
@RequestMapping("action")
@Produces(MediaType.APPLICATION_JSON)
public class ActionResource {
    @PostMapping("add")
    public void addTransaction(Transaction transaction) throws IOException {
        blockChainService.getChain().addTransaction(transaction);
    }
}
