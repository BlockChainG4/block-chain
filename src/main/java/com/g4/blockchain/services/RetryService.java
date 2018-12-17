package com.g4.blockchain.services;

import com.g4.blockchain.BlockChain;
import com.g4.blockchain.Peer;
import com.g4.blockchain.Peers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.ConnectException;
import java.net.UnknownHostException;

@Service
public class RetryService {

    @Inject
    RestTemplate restTemplate;

    @Value("${peer.self}")
    private String self;

    @Retryable(value = {UnknownHostException.class, ConnectException.class}, maxAttempts = 10, backoff = @Backoff(10000))
    public Peer addPeer(String peer) {
        Peer peerRequest = new Peer();
        peerRequest.setAddress(self);
        return restTemplate.postForObject("http://".concat(peer).concat(":8080/peer"), peerRequest, Peer.class);
    }


    public Peers getPeers(String peer) {
        return restTemplate.getForEntity("http://".concat(peer).concat(":8080/peer"), Peers.class).getBody();
    }

    public Boolean ping(String peer) {
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity("http://".concat(peer).concat(":8080/peer/ping"), Boolean.class);
        return responseEntity.getStatusCode().equals(HttpStatus.OK);
    }

    public BlockChain getLatestChain(String peer) {
        // Find latest chain and return that one.
        return restTemplate.getForEntity("http://".concat(peer).concat(":8080/peer/block_chain"), BlockChain.class).getBody();
    }

    @Recover
    public String recover(Throwable t) {
        return "Error Class :: " + t.getClass().getName();
    }
}
