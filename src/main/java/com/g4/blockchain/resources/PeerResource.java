package com.g4.blockchain.resources;

import com.g4.blockchain.peerresource.Peer;
import com.g4.blockchain.peerresource.PeerController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("peer")
@Produces(MediaType.APPLICATION_JSON)
public class PeerResource {

    private static PeerController ctrl = new PeerController();

//    @GetMapping
//    public String getSomething() {
//        var something = "Something";
//        return something;
//    }

    @GetMapping("/{name}")
    public Peer getPeer(@PathVariable("name") String name){
        System.out.println("HERE I AM");
        return ctrl.getPeer(name);
    }

    @PostMapping
    public Peer addPeer(@RequestBody @Valid Peer peer) {
        System.out.println("HERE I AM ADDING PEER" + peer.getName() + ":" + peer.getPort());
        return ctrl.addPeer(peer.getName(), peer.getPort());
    }

}
