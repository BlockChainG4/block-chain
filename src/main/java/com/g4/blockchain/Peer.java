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
}
