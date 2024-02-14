package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class TransferInput implements SubsystemInput {

    public boolean active;
    public boolean coastModeEnabled;
    public TransferSubsystem.TransferStatus mode;
    @Override
    public void toLog(LogTable table) {
    }
    @Override
    public void fromLog(LogTable table) {
    }

}
