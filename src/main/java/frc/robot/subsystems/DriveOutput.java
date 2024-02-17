package frc.robot.subsystems;


import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemOutput;

@AutoLog
public class DriveOutput implements SubsystemOutput {
    public SwerveModulePosition[] modulePositions;
    @Override
    public void log() {

    }
}
