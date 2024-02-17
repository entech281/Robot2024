package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class TransferInput implements SubsystemInput {

    public boolean activate = false;
    public boolean brakeModeEnabled = false;
    public TransferSubsystem.TransferStatus currentMode = TransferSubsystem.TransferStatus.Off;
    @Override
    public void toLog(LogTable table) {
    }
    @Override
    public void fromLog(LogTable table) {
    }

}
