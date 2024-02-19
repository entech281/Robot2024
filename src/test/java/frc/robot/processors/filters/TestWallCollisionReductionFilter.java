package frc.robot.processors.filters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestWallCollisionReductionFilter {
  public static final double TOLERANCE = 0.00001;

  @Test
  void testCalculateCorrectCord() {
    assertEquals(2.94, WallCollisionReductionFilter.calculateCorrectCord(3, -3), TOLERANCE);
    assertEquals(3.06, WallCollisionReductionFilter.calculateCorrectCord(3, 3), TOLERANCE);
  }

  @Test
  void textCalculateNextSecondCord() {
    assertEquals(-0.06, WallCollisionReductionFilter.calculateNextSecondCord(3, -3), TOLERANCE);
    assertEquals(6.06, WallCollisionReductionFilter.calculateNextSecondCord(3, 3), TOLERANCE);
  }

  @Test
  void testCalculateLimit() {
    assertEquals(0.444444, WallCollisionReductionFilter.calculateLimit(1, 2), TOLERANCE);
    assertEquals(0.871111, WallCollisionReductionFilter.calculateLimit(1.4, -2), TOLERANCE);
  }
}
