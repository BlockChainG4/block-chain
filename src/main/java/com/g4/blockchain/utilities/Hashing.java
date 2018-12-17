package com.g4.blockchain.utilities;

import com.g4.blockchain.Transaction;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Hashing {
    // Implement 2 different ways of hashing block info
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMerkleRoot(LinkedList<Transaction> transactions) {
            List<String> treeList = merkleTree(transactions);
            // Last element is the merkle root hash if transactions
            return treeList.get(treeList.size()-1);
    }

    public static List<String> merkleTree(LinkedList<Transaction> transactions) {
        ArrayList<String> tree = new ArrayList<>();
        // add all transactions as leaves of the tree.
        for (Transaction t : transactions) {
            tree.add(t.getHash());
        }
        int levelOffset = 0; // first level

        // Iterate through each level, stopping when we reach the root (levelSize
        // == 1).
        for (int levelSize = transactions.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            // For each pair of nodes on that level:
            for (int left = 0; left < levelSize; left += 2) {
                // The right hand node can be the same as the left hand, in the
                // case where we don't have enough
                // transactions.
                int right = Math.min(left + 1, levelSize - 1);
                String tleft = tree.get(levelOffset + left);
                String tright = tree.get(levelOffset + right);
                tree.add(Hashing.applySha256(tleft + tright));
            }
            // Move to the next level.
            levelOffset += levelSize;
        }
        return tree;
    }
}
