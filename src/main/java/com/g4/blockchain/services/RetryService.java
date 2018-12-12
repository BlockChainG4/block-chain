package com.g4.blockchain.services;

import com.g4.blockchain.Peer;
import com.g4.blockchain.Peers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.UnknownHostException;

@Service
public class RetryService {

    @Inject
    RestTemplate restTemplate;

    @Retryable(value = {UnknownHostException.class}, backoff = @Backoff(5000))
    public Peers addPeer(String peer) throws UnknownHostException {
        Peer peerRequest = new Peer();
        peerRequest.setAddress(peer);
        return restTemplate.postForObject("http://".concat(peer).concat(":8080/peer"), peerRequest, Peers.class);
    }

    @Recover
    public String recover(Throwable t) {
        return "Error Class :: " + t.getClass().getName();
    }
}
