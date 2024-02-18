package frc.robot.io;


import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;


// refer here: for how they do it
// https://github.com/Mechanical-Advantage/AdvantageKit/blob/main/junction/autolog/src/org/littletonrobotics/junction/AutoLogAnnotationProcessor.java
public class DebugInput implements LoggableInputs {

  public static final String SHOOTER_SPEED = "shooterSpeed";
  public double shooterSpeed = 0.0;

  @Override
  public void toLog(LogTable table) {
    table.put(SHOOTER_SPEED, shooterSpeed);
  }

  @Override
  public void fromLog(LogTable table) {
    shooterSpeed = table.get(SHOOTER_SPEED, shooterSpeed);
  }
}
