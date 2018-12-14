package com.g4.blockchain.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g4.blockchain.Block;
import org.springframework.stereotype.Component;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

@Component
public class Hasher {
    // Implement 2 different ways of hashing block info

    private ObjectMapper objectMapper = new ObjectMapper();

    public String hashBlock(Block block) {
        try {
            return hash(objectMapper.writeValueAsString(block));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String hash(String content) {
        String hash = Hashing
                .sha256()
                .hashString(content, StandardCharsets.UTF_8)
                .toString();

        return hash;

    }
}
