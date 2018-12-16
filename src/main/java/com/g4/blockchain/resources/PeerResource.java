package com.g4.blockchain.resources;

import com.g4.blockchain.Peer;
import com.g4.blockchain.PeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("peer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeerResource {

    private Logger logger = LoggerFactory.getLogger(PeerResource.class);

    @Autowired
    private PeerRepository repository;

    @Value("${peer.self}")
    private String self;

    @GetMapping
    public Iterable<Peer> getPeers() {
        return repository.findAll();
    }

    @PostMapping
    public Peer addPeer(@RequestBody Peer peer) {
        if (peer.getAddress().equals(self)) {
            logger.error("Can't add self to peer list");
            throw new RuntimeException("Could not add self to peer list");
        }
        if (!repository.existsById(peer.getAddress())) {
            return repository.save(peer);
        } else {
            throw new RuntimeException("Peer already added");
        }
    }

    @GetMapping(path = "ping")
    public Boolean ping() {
        return true;
    }

}
