package com.gormlab.randomdraw.model;

public class WeightedEntry implements Cloneable {
    private final String entryId;
    private int weight;

    public WeightedEntry(String entryId, int weight) {
        this.entryId = entryId;
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public String getEntryId() {
        return entryId;
    }

    public WeightedEntry clone() {
        return new WeightedEntry(this.entryId, this.weight);
    }
}
