package frc.robot.subsystems.shooter;

import entech.subsystems.SubsystemInput;
import org.littletonrobotics.junction.LogTable;

public class ShooterInput implements SubsystemInput {

  public boolean activate = false;
  public double speed = 0;
  public boolean brakeModeEnabled = false;

  @Override
  public void toLog(LogTable table) {
    table.put("shooterInput/active", activate);
    table.put("shooterInput/speed", speed);
    table.put("shooterInput/brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {
  }
}
