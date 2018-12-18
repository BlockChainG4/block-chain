package com.g4.blockchain;

import com.g4.blockchain.services.BlockChainService;
import com.g4.blockchain.services.RetryService;
import com.g4.blockchain.utilities.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(TestRunner.class);

    @Value("#{'${initial.nodes}'.split(',')}")
    private List<String> initialPeers;

    @Value("${peer.self}")
    private String self;

    @Inject
    private RetryService retryService;

    @Inject
    private BlockChainService blockChainService;

    @Inject
    private FileWriter fileWriter;

    @Value("${blockchain.file.name}")
    private String blockChainFileName;

    @Override
    public void run(String... args) throws Exception {
       List<String> peersAdded = new ArrayList<>();
        do {
            for (String peer : initialPeers) {
                if (peer.equals(self)) continue;
                Peer p = retryService.addPeer(peer);
                peersAdded.add(p.getAddress());
            }
        } while (peersAdded.size() < 1);
        logger.info("Peer " + self + " is done adding itself to peers");

        BlockChain chain = null;
        for (String peer : peersAdded) {
            BlockChain c = retryService.getLatestChain(peer);
            if (chain == null) chain = c;
            if (chain != null && c.size() > chain.size()) {
                if (blockChainService.consensus(c, chain)) {
                    chain = c;
                }
            }
        }
        fileWriter.save(chain, blockChainFileName);

    }
}
