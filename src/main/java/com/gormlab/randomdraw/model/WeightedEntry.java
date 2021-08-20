package com.gormlab.randomdraw.model;

import java.util.Objects;

public class WeightedEntry implements Cloneable, Comparable<WeightedEntry> {
    private final String entryId;
    private int weight;

    public WeightedEntry(String entryId, int weight) {
        this.entryId = entryId;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getEntryId() {
        return entryId;
    }

    public WeightedEntry clone() {
        return new WeightedEntry(this.entryId, this.weight);
    }

    @Override
    public int compareTo(WeightedEntry otherEntry) {
        return this.entryId.compareTo(otherEntry.entryId);
    }

    @Override
    public boolean equals(Object obj) {
        WeightedEntry otherObject = (WeightedEntry) obj;
        return this.entryId.equals(otherObject.entryId) && this.weight == otherObject.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId);
    }

    @Override
    public String toString() {
        return entryId;
    }
}
