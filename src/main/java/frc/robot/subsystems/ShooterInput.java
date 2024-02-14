package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class ShooterInput implements SubsystemInput {

    public boolean active;
    public double maxSpeed;
    public boolean coastModeEnabled;
    @Override
    public void toLog(LogTable table) {
    }
    @Override
    public void fromLog(LogTable table) {
    }

}
