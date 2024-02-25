package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import entech.util.IsWithinTolerance;

public class TestWithinTolerance {

  private static final double TOLERANCE = 1;

  @Test
  void TestWithinToleranceExact() {
    assertEquals(true, IsWithinTolerance.isWithinTolerance(TOLERANCE, 10, 10));
  }

  @Test
  void TestWithinToleranceNotExact() {
    assertEquals(true, IsWithinTolerance.isWithinTolerance(TOLERANCE, 10.5, 10));
    assertEquals(true, IsWithinTolerance.isWithinTolerance(TOLERANCE, 9.5, 10));
  }

  @Test
  void TestWithinToleranceLower() {
    assertEquals(false, IsWithinTolerance.isWithinTolerance(TOLERANCE, 11.5, 10));
    assertEquals(false, IsWithinTolerance.isWithinTolerance(TOLERANCE, 8.5, 10));
  }
}
