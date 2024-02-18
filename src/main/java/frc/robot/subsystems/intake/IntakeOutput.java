package frc.robot.subsystems.intake;

import entech.subsystems.SubsystemOutput;

public class IntakeOutput implements SubsystemOutput {

  public boolean active;
  public double currentSpeed;
  public boolean brakeModeEnabled;

  @Override
  public void log() {}
}
