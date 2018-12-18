package com.g4.blockchain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        // Read from file and get the entire block chain
        return null;
    }

    // When mining is done
    public void broadCastMining(String address) {
        BlockChain chain = retryService.getLatestChain(address);

        // Replace comparison with stored blockchain
        if (chain.get(chain.size() - 1).getTimeStamp() < chain.get(chain.size() - 1).getTimeStamp()) {
            // It is newer than the one we have here
            // so we save it to a file and broadcast it to all out peers that we have a newer one

            retryService.broadCastNewChain(self);
        }
    }

    public void broadCastResult(Block block) throws JsonProcessingException {
        // Check if incoming block is newer than latest in chain
        // and that the previous hash is the same. If not, ignore
        if (block.getTimeStamp() < block.getTimeStamp() && block.getPreviousHash().equals(block.getPreviousHash())) {
            Iterable<Peer> peers = peerRepository.findAll();
            peers.forEach(peer -> retryService.broadCastResult(peer.getAddress(), block));
            block.mineBlock(BlockChain.DIFFICULTY);
            // Filewriter read current blockchain
            // Append hash to list, write to file
            // broadcast mining is done.
        }
        // Send the block to all other known peers if it's the latest one and begin mining the block
    }
}
