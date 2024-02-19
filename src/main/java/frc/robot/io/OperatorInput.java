package frc.robot.io;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;


public class OperatorInput implements LoggableInputs {
  @Override
  public void toLog(LogTable table) {
    // log all the fields when they are added
  }

  @Override
  public void fromLog(LogTable table) {
    // read all the fields when they are added
  }
}
