package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;

import entech.subsystems.SubsystemOutput;

public class ShooterOutput implements SubsystemOutput {

    public double speed;
    public boolean active;
    public boolean brakeModeEnabled;

    @Override
    public void log() {
        Logger.recordOutput("shooterOutputOutput/active", active);
        Logger.recordOutput("shooterOutputOutput/speed", speed);
        Logger.recordOutput("shooterOutputOutput/brakeModeEnabled", brakeModeEnabled);
    }

}