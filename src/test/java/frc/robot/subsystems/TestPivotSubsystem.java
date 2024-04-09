package frc.robot.subsystems;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.pivot.PivotSubsystem;


class TestPivotSubsystem {
  public static final double TOLERANCE = 0.00001;

  @Test
  void testPivotConversion() {
    assertEquals(30.0, PivotSubsystem.calculateMotorPositionFromDegrees(72.0), TOLERANCE);
  }
}
