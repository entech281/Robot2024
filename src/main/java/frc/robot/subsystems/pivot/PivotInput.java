package frc.robot.subsystems.pivot;

import entech.subsystems.SubsystemInput;
import org.littletonrobotics.junction.LogTable;

public class PivotInput implements SubsystemInput {

  public double requestedPosition = 0.0;
  public boolean brakeModeEnabled = false;

  @Override
  public void toLog(LogTable table) {
    table.put("Requested position", requestedPosition);
  }

  @Override
  public void fromLog(LogTable table) {}



}
