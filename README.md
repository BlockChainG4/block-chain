# Block-chain

> **Authors:**
> - Mikkel Ziemer Højbjerg Jensen
> - Diana Strele
> - Cherry Rose Jimenez Semena
> - Mahnaz Karimi

---

_Requirements_:
- [x] blockchain in general(add transaction, mining blocks, consensus algorithm, etc.)
- [x] peer-to-peer network
- [x] 4 nodes
- [x] reproducible setup - Bash Script(Test Scenario & Screencast)

## Documentation:

### I. Our Solution

_**Blockchain Implementation**_

Our blockchain keeps history of mathematical calculations made from incoming transaction(s). 

```
http://localhost:8081/action/transaction
```

POST:
```json
{
"operation" : "2+2"
}
```

```json
[
    {
        "previousHash": "0",
        "hash": "c44d9a9548990dd6cf95901a0f94a58e7d20cde06d67c393f49a5d90fc5c2b55",
        "timeStamp": 1545152758880,
        "nonce": 0,
        "transactions": [
            {
                "hash": null,
                "operation": "2+2",
                "result": "4"
            }
        ],
        "merkleRoot": null
    }
]
```

When a new peer joins the network, it uses consensus algorithm to get the longest and the latest updated chain.

Blockchain system sequence:
* Node gets transaction(s)
* User requests node to start mining
* Node broadcasts new chain with added block containing incoming transaction to be mined
* All the nodes start mining
* First to finish mining, broadcasts result
* Others gets the result
* Others validate the result
* If validate is true, update the chain

The blockchain  is structures as linked list. Each block in the chain contains previous blocks hash, and their own hash, created from all the data in the block, so called digital signature, linking blocks in a chain. Blockchain is a constantly growing chain of ordered information. Everytime, the chain adds a transaction, we save the whole chain to a file locally. Once application is closed and restarted, the file with transactions and hashes is overwritten with the new chain info.



_**P2P Network**_

![image](https://user-images.githubusercontent.com/16150075/50159949-509a6d00-02d8-11e9-9913-dc95358c8e72.png)

In order to ensure blockchain is secure and  up-to-date, it works in a peer-to-peer network. A node is represented as a peer. Each node stores the complete and updated version of the blockchain. The good thing in p2p network is that it doesn't depend on one party to define the state of the blockchain. Having multiple nodes makes it difficult for a malicious act to be performed as it will also be needed to be performed in all the nodes, and help to decentralized network.

We used docker containers for initializing the 4 peers. It is predefined using docker-compose file that would run in ports — `8081`,` 8082`,`8083`, and `8084`.

See here: [docker-compose.yml](https://github.com/BlockChainG4/block-chain/blob/master/docker-compose.yml)

### II. Reproducible Setup

How to use:

* Clone project
* Have docker running
* Java 11
* Maeven
* Execute `./run.sh`

![Watch screencast](https://github.com/BlockChainG4/block-chain/blob/e3a29f2187d85e232a47c127fdf474ab2593f243/ScreenCast.mov)

To attach a new peer:

* `docker build .`
* `docker run --name peer6 -e PEER_SELF=peer6 --network=g4blockchain -p 8086:8080 1418d6df4c6c`


### III. Conclusion

Blockchain has reinvented the way of transactions for economy, record keeping, cyveillance, security, privacy and many other areas of importance. Understanding the way blockchain works isn't easy, but once starting to build our own, it became more clear on how blockchain runs and the role of decentralized network. 

So, basically we made a blockchain — a data structure hosted on a peer-to-peer network of participating 4 nodes/peers and miners that follows the consensus protocol, set of rules in adding the blocks to the chain, and whether blocks/blockchain itself are valid.

### IV. References

* Understanding Blockchain - https://hackernoon.com/3-steps-to-understanding-blockchain-8a285572daa3
* Tutorial in Python - https://hackernoon.com/learn-blockchains-by-building-one-117428612f46
* Tutorial in Java - https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
* 51% Attack Bitcoin Tutorial - https://www.youtube.com/watch?annotation_id=annotation_2086342533&feature=iv&src_vid=6luEMwSAS0I&v=DHa5w1jWGuw
