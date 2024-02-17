package frc.robot.subsystems;

import entech.subsystems.SubsystemOutput;

public class TransferOutput implements SubsystemOutput {

    public boolean active;
    public boolean brakeModeEnabled;
    public TransferSubsystem.TransferStatus currentMode;

    @Override
    public void log() {
    }

}
