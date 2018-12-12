package com.g4.blockchain.resources;

import com.g4.blockchain.Peer;
import com.g4.blockchain.Peers;
import com.g4.blockchain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("peer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeerResource {

    @Autowired
    Storage storage;

    @GetMapping
    public Peers getPeers() {
        return storage.getPeers();
    }

    @PostMapping
    public Peers addPeer(@RequestBody Peer peer) {
        storage.getPeers().add(peer);
        return storage.getPeers();
    }

    @GetMapping(path = "ping")
    public void ping() {
    }

}
