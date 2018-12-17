package com.g4.blockchain.services;

import com.g4.blockchain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlockChainService {

    @Autowired
    private RetryService retryService;

    @Autowired
    private PeerRepository peerRepository;

    @Value("${peer.self}")
    private String self;

    public BlockChain getChain() {
        return null;
    }

    public void broadCastMining(String address) {
        BlockChain chain = retryService.getLatestChain(address);

        // Replace comparison with stored blockchain
        if (chain.getHead().getTimeStamp() < chain.getHead().getTimeStamp()) {
            // It is newer than the one we have here
            // so we save it to a file and broadcast it to all out peers that we have a newer one

            retryService.broadCastNewChain(self);
        }
    }

    public void broadCastResult(Block block) {
        // Check if incoming block is newer than latest in chain
        // and that the previous hash is the same. If not, ignore
        if (block.getTimeStamp() < block.getTimeStamp() && block.getPreviousHash().equals(block.getPreviousHash())) {
            Iterable<Peer> peers = peerRepository.findAll();
            peers.forEach(peer -> retryService.broadCastResult(peer.getAddress(), block));
        }
        // Send the block to all other known peers if it's the latest one and begin mining the block
    }
}
