package frc.robot.subsystems;

import entech.subsystems.SubsystemOutput;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;


public class NavxOutput implements SubsystemOutput {
    public double yawAngle = 0.0;
    public double pitchAngle = 0.0;
    public double rollAngle = 0.0;

    @Override
    public void log() {
        Logger.recordOutput(getLogName("yawAngle"),yawAngle);
        Logger.recordOutput(getLogName("pitchAngle"),pitchAngle);
        Logger.recordOutput(getLogName("rollAngle"),rollAngle);
    }
}
