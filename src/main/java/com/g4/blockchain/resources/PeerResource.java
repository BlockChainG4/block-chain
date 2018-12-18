package com.g4.blockchain.resources;

import com.g4.blockchain.*;
import com.g4.blockchain.services.BlockChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@RestController
@RequestMapping("peer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeerResource {

    private Logger logger = LoggerFactory.getLogger(PeerResource.class);

    @Autowired
    private PeerRepository repository;

    @Autowired
    private BlockChainService blockChainService;

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

    @GetMapping(path = "block_chain")
    public BlockChain getChain() throws IOException {
        return blockChainService.getChain();
    }


    /**
     * Call this method to tell your peers that you have a newer updated version
     * of the chain and they should query you for it
     * @param address
     */
    @PutMapping(path = "broadcast_mining/{address}")
    public void broadCastMining(@PathVariable("address") String address) throws Exception {
        blockChainService.broadCastMining(address);
    }

    @PostMapping(path = "broadcast_result")
    public void broadCastResult(@RequestBody Block block) throws Exception {
        blockChainService.broadCastResult(block);
    }

    @GetMapping(path = "ping")
    public Boolean ping() {
        return true;
    }

}
