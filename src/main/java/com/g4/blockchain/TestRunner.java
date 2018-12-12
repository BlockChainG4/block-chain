package com.g4.blockchain;

import com.g4.blockchain.services.RetryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {
    @Value("#{'${initial.nodes}'.split(',')}")
    private List<String> initialPeers;

    @Value("${peer.self}")
    private String self;

    @Inject
    RetryService retryService;

    @Override
    public void run(String... args) throws Exception {
        Peers peers = new Peers();
        while (true) {
            for (String peer : initialPeers) {
                if (peer.equals(self)) continue;
                peers = retryService.addPeer(peer);
                System.out.println(peers.size());
            }
            if (peers.size() > 2) break;
        }
    }
}
