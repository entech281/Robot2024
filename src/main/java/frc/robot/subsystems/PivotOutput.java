package frc.robot.subsystems;

import entech.subsystems.SubsystemOutput;

public class PivotOutput implements SubsystemOutput{

    public boolean moving = false;
    public boolean brakeModeEnabled = false;
    public double currentPosition = 0.0;

    @Override
    public void log() {
    }

}
