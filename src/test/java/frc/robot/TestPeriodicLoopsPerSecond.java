package frc.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import entech.util.PeriodicLoopsPerSecond;

public class TestPeriodicLoopsPerSecond {

  @Test
  void testPeriodicLoopsPerSecond() {
    assertEquals(50, PeriodicLoopsPerSecond.getLoopsPerSecond(1));
    assertEquals(100, PeriodicLoopsPerSecond.getLoopsPerSecond(2));
    assertEquals(500, PeriodicLoopsPerSecond.getLoopsPerSecond(10));
  }
}
