package entech.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestStoppingCounter {

    @Test
    public void testStopperStopsAllSuccess() {
        final int NUM_COUNTS = 4;

        StoppingCounter sc = new StoppingCounter("quicketest", NUM_COUNTS);
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertTrue(sc.isFinished(true));
    }

    @Test
    public void testStopperResets() {
        final int NUM_COUNTS = 4;

        StoppingCounter sc = new StoppingCounter("quicketest", NUM_COUNTS);
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(false));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertFalse(sc.isFinished(true));
        assertTrue(sc.isFinished(true));
    }

}
