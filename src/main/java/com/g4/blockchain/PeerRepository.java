package com.g4.blockchain;

import org.springframework.data.repository.CrudRepository;

public interface PeerRepository extends CrudRepository<Peer, String> {
}
