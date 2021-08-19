package com.gormlab.randomdraw.controller;

import com.gormlab.randomdraw.model.WeightedEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DrawMaster {
    private final Random random;

    public DrawMaster(Random random) {
        this.random = random;
    }

    public List<WeightedEntry> draw(List<WeightedEntry> weightedEntries, int numberOfWinners, int seed) {
        random.setSeed(seed);
        return draw(weightedEntries, numberOfWinners);
    }

    public List<WeightedEntry> draw(List<WeightedEntry> weightedEntries, int numberOfWinners) {
        List<WeightedEntry> winners = new ArrayList<>();
        for (int i = 0; i < numberOfWinners; i++) {
            int nextWinner = random.nextInt(getRemainingEntryCount(weightedEntries)) + 1;
            WeightedEntry winner = drawWinner(weightedEntries, nextWinner);
            winners.add(winner.clone());
            winner.setWeight(0);
        }

        return winners;
    }

    private int getRemainingEntryCount(List<WeightedEntry> weightedEntries) {
        int count =0;
        for(WeightedEntry weightedEntry : weightedEntries) {
            count += weightedEntry.getWeight();
        }
        return count;
    }


    public List<WeightedEntry> draw(List<WeightedEntry> weightedEntries) {
        return draw(weightedEntries, 1);
    }

    private WeightedEntry drawWinner(List<WeightedEntry> weightedEntries, int nextWinner) {
        for (WeightedEntry entry : weightedEntries) {
            nextWinner = nextWinner - entry.getWeight();
            if (nextWinner <= 0) {
                return entry;
            }
        }

        throw new IndexOutOfBoundsException("Random number was out or range");
    }


}
