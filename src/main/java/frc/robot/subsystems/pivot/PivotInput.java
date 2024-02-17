package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.LogTable;

import entech.subsystems.SubsystemInput;

public class PivotInput implements SubsystemInput{

    public double requestedPosition = 0.0;
    public boolean brakeModeEnabled = false;

    @Override
    public void toLog(LogTable table) {
        table.put("Requested position", requestedPosition);
    }

    @Override
    public void fromLog(LogTable table) {
    }



}
