package frc.robot.subsystems.navx;

import org.ejml.simple.UnsupportedOperation;
import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class NavXInput implements SubsystemInput {
  @Override
  public void fromLog(LogTable table) {
    throw new UnsupportedOperation();
  }

  @Override
  public void toLog(LogTable table) {
    throw new UnsupportedOperation();
  }
}
