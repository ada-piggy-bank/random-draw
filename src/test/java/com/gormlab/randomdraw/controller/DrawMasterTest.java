package com.gormlab.randomdraw.controller;

import com.gormlab.randomdraw.model.WeightedEntry;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrawMasterTest {

    public static final int INITIAL_ENTRIES = 20;
    public static final int REDUCED_ENTRIES = 10;
    public static final int ENTRIES_PER_TICKET = REDUCED_ENTRIES;
    @Mock
    Random random;
    @InjectMocks
    private DrawMaster drawmaster;
    @Spy
    private WeightedEntry weightedEntry = new WeightedEntry("Entry One", ENTRIES_PER_TICKET);
    @Spy
    private WeightedEntry clonedWeightedEntry = new WeightedEntry("Entry One", ENTRIES_PER_TICKET);
    @Spy
    private WeightedEntry weightedEntryTwo = new WeightedEntry("Entry Two", ENTRIES_PER_TICKET);
    @Spy
    private WeightedEntry clonedWeightedEntryTwo = new WeightedEntry("Entry Two", ENTRIES_PER_TICKET);

    private List<WeightedEntry> weightedEntries;

    @BeforeEach
    public void setUp() {
        weightedEntries = Arrays.asList(weightedEntry, weightedEntryTwo);
        lenient().when(random.nextInt(INITIAL_ENTRIES)).thenReturn(5);
        lenient().when(random.nextInt(REDUCED_ENTRIES)).thenReturn(5);
        lenient().when(weightedEntry.clone()).thenReturn(clonedWeightedEntry);
        lenient().when(weightedEntryTwo.clone()).thenReturn(clonedWeightedEntryTwo);
    }

    @Test
    public void drawSelectsNoEntriesIfTooHigh_ThrowsException() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(10000);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> drawmaster.draw(weightedEntries));

        String expectedMessage = "Random number was out or range";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, is(expectedMessage));
    }

    @Test
    public void drawSetsWeightToZero() {
        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntry).toString()));
    }

    @Test
    public void remainingEntriesHaveOriginalWeight() {
        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo, never()).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntry).toString()));
    }

    @Test
    public void drawSelectsRandomFirstEntry() {
        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo, never()).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntry).toString()));
    }

    @Test
    public void drawSelectsRandomSecondEntry() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(15);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntryTwo).setWeight(0);
        verify(weightedEntry, never()).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntryTwo).toString()));
    }

    @Test
    public void drawSelectsRandomFirstEntryOnLowBoundary() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(0);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo, never()).setWeight(0);
        assertEquals(winners.toString(), Collections.singletonList(weightedEntry).toString());
    }

    @Test
    public void drawSelectsRandomFirstEntryOnHighBoundary() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(9);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo, never()).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntry).toString()));
    }

    @Test
    public void drawSelectsRandomSecondEntryOnLowBoundary() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(10);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries);

        verify(weightedEntry, never()).setWeight(0);
        verify(weightedEntryTwo).setWeight(0);
        assertThat(winners.toString(), is(Collections.singletonList(weightedEntryTwo).toString()));
    }

    @Test
    public void drawSelectsTwoWinnersInOrder() {


        List<WeightedEntry> winners = drawmaster.draw(weightedEntries, 2);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo).setWeight(0);
        assertThat(winners.toString(), is(Arrays.asList(weightedEntry, weightedEntryTwo).toString()));
    }

    @Test
    public void drawSelectsTwoWinnersAnotherOrder() {
        when(random.nextInt(INITIAL_ENTRIES)).thenReturn(15);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries, 2);

        verify(weightedEntry).setWeight(0);
        verify(weightedEntryTwo).setWeight(0);
        assertThat(winners.toString(), is(Arrays.asList(weightedEntryTwo, weightedEntry).toString()));
    }

    @Test
    public void randomNumberRangeIsSetWithInitialEntryCount() {
        drawmaster.draw(weightedEntries);

        verify(random).nextInt(INITIAL_ENTRIES);
    }

    @Test
    public void randomNumberRangeIsSetWithInitialEntryCountThenSecondCount() {
        drawmaster.draw(weightedEntries, 2);

        InOrder inOrder = inOrder(random);
        inOrder.verify(random).nextInt(INITIAL_ENTRIES);
        inOrder.verify(random).nextInt(REDUCED_ENTRIES);
    }

    @Test
    public void randomIsInitialisedWithSeed() {
        List<WeightedEntry> winners = drawmaster.draw(weightedEntries, 2, 1);

        verify(random).setSeed(1);

        assertThat(winners.toString(), is(Arrays.asList(weightedEntry, weightedEntryTwo).toString()));

    }

    @Test
    public void entriesAreOrderedAlphabeticallyBeforeDraw() {
        List<WeightedEntry> unOrderedWeightedEntries = Arrays.asList(weightedEntryTwo, weightedEntry);

        List<WeightedEntry> winners = drawmaster.draw(weightedEntries, 2, 1);

        assertThat(winners.toString(), is(Arrays.asList(weightedEntry, weightedEntryTwo).toString()));
    }

}