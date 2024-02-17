package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class ShooterInput implements SubsystemInput {

    public boolean activate = false;
    public double speed = 0;
    public boolean brakeModeEnabled = false;

    @Override
    public void toLog(LogTable table) {
    }
    @Override
    public void fromLog(LogTable table) {
    }

}
