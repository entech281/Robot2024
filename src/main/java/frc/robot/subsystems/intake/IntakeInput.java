package frc.robot.subsystems.intake;

import entech.subsystems.SubsystemInput;
import org.littletonrobotics.junction.LogTable;

public class IntakeInput implements SubsystemInput {

  public boolean activate;
  public boolean brakeModeEnabled;

  @Override
  public void toLog(LogTable table) {
  }

  @Override
  public void fromLog(LogTable table) {
  }

}
