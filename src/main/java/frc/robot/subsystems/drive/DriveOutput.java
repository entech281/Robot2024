package frc.robot.subsystems.drive;


import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemOutput;

@AutoLog
public class DriveOutput implements SubsystemOutput {
    public SwerveModulePosition[] modulePositions;

    @Override
    public void log() {
        Logger.recordOutput("driveOutput", modulePositions);
    }
}
