package entech.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestEntechUtils {
  @Test
  void testPeriodicLoopsPerSecond() {
    assertEquals(50, EntechUtils.getLoopsPerSecond(1));
    assertEquals(100, EntechUtils.getLoopsPerSecond(2));
    assertEquals(500, EntechUtils.getLoopsPerSecond(10));
  }

  private static final double TOLERANCE = 1;

  @Test
  void TestWithinToleranceExact() {
    assertEquals(true, EntechUtils.isWithinTolerance(TOLERANCE, 10, 10));
  }

  @Test
  void TestWithinToleranceNotExact() {
    assertEquals(true, EntechUtils.isWithinTolerance(TOLERANCE, 10.5, 10));
    assertEquals(true, EntechUtils.isWithinTolerance(TOLERANCE, 9.5, 10));
  }

  @Test
  void TestWithinToleranceLower() {
    assertEquals(false, EntechUtils.isWithinTolerance(TOLERANCE, 11.5, 10));
    assertEquals(false, EntechUtils.isWithinTolerance(TOLERANCE, 8.5, 10));
  }
}
