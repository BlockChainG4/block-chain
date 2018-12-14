package com.g4.blockchain;

public class Peer {
    private String address;

    public Peer() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        // If the object is compared with itself then return true
        if (other == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(other instanceof Peer)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Peer peer = (Peer) other;

        return peer.address.equals(this.address);
    }
}
