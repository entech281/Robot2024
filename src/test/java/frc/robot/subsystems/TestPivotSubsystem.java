package frc.robot.subsystems;

import frc.robot.subsystems.pivot.PivotSubsystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestPivotSubsystem {

  public static final double TOLERANCE = 0.00001;

  @Test
  public void testPivotConversion(){
    assertEquals(30.0, PivotSubsystem.calculateMotorPositionFromDegrees(72.0), TOLERANCE);
  }



}
