package com.g4.blockchain.models;

import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    int sender;
    int receiver;
    TRANSACTION_TYPE type;
    List<Block> blocks;

    public enum TRANSACTION_TYPE {
        READY, INFO_NEW_BLOCK, REQ_ALL_BLOCKS, RSP_ALL_BLOCKS
    }

    @Override
    public String toString() {
        return String.format("Message {type=%s, sender=%d, receiver=%d, blocks=%s}", type, sender, receiver, blocks);
    }

    static class MessageBuilder {
        private final Transaction message = new Transaction();

        MessageBuilder withSender(final int sender) {
            message.sender = sender;
            return this;
        }

        MessageBuilder withReceiver(final int receiver) {
            message.receiver = receiver;
            return this;
        }

        MessageBuilder withType(final TRANSACTION_TYPE type) {
            message.type = type;
            return this;
        }

        MessageBuilder withBlocks(final List<Block> blocks) {
            message.blocks = blocks;
            return this;
        }

        Transaction build() {
            return message;
        }

    }
}
