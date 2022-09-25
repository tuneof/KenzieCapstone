package com.kenzie.capstone.service.model;

public class TopHires {
    private int numHires;
    private String freelancerId;

    public TopHires(int numHires, String freelancerId) {
        this.numHires = numHires;
        this.freelancerId = freelancerId;
    }

    public TopHires() {}

    public int getNumHires() {
        return numHires;
    }

    public void setNumHires(int numHires) {
        this.numHires = numHires;
    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }
}
