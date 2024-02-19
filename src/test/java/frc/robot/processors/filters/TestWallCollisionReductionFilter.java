package frc.robot.processors.filters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestWallCollisionReductionFilter {
  public static final double TOLERANCE = 0.00001;

  @Test
  public void testCalculateCorrectCord() {
    assertEquals(WallCollisionReductionFilter.calculateCorrectCord(3, -3), 2.94, TOLERANCE);
    assertEquals(WallCollisionReductionFilter.calculateCorrectCord(3, 3), 3.06, TOLERANCE);
  }
}
