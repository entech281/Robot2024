package frc.robot.processors.filters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

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

  @Test
  void testDoDirectionalLimit() {
    assertEquals(1.0, testHelperForDDL(5.0, 3.0, 1.0, Alliance.Blue), TOLERANCE);
    assertEquals(0.246098962963, testHelperForDDL(6.0, 3.0, 1.0, Alliance.Blue), TOLERANCE);
    assertEquals(-0.4096, testHelperForDDL(3.0, -2.0, -1.0, Alliance.Blue), TOLERANCE);
    assertEquals(1.0, testHelperForDDL(3.0, -2.0, 1.0, Alliance.Blue), TOLERANCE);
    assertEquals(0.4096, testHelperForDDL(3.0, -2.0, 1.0, Alliance.Red), TOLERANCE);
    assertEquals(-1.0, testHelperForDDL(3.0, -2.0, -1.0, Alliance.Red), TOLERANCE);
    assertEquals(1.0, testHelperForDDL(5.0, 3.0, 1.0, Alliance.Red), TOLERANCE);
    assertEquals(0.246098962963, testHelperForDDL(4.0, -3.0, 1.0, Alliance.Red), TOLERANCE);
    assertEquals(-0.4096, testHelperForDDL(3.0, -2.0, -1.0, Alliance.Blue), TOLERANCE);
  }

  private double testHelperForDDL(double cord, double speed, double percent, Alliance team) {
    return WallCollisionReductionFilter.doDirectionalLimit(cord, 10, speed, percent, team);
  }
}
