
package com.g4.blockchain;

import com.g4.blockchain.services.RetryService;
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

    @Override
    public void run(String... args) throws InterruptedException {
       List<String> peersAdded = new ArrayList<>();
        while (true) {
            for (String peer : initialPeers) {
                if (peer.equals(self)) continue;
                Peer p = retryService.addPeer(peer);
                peersAdded.add(p.getAddress());
            }
            if (peersAdded.size() > 2) break;
        }
        logger.info("Peer " + self + " is done adding itself to peers");
    }
}
