package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class PivotInput implements SubsystemInput{

    public double requestedPosition = 0.0;
    public boolean brakeModeEnabled = false;

    @Override
    public void toLog(LogTable table) {
    }

    @Override
    public void fromLog(LogTable table) {
    }



}
