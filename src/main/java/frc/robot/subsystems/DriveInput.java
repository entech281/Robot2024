package frc.robot.subsystems;

import org.littletonrobotics.junction.LogTable;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.subsystems.SubsystemInput;

public class DriveInput implements SubsystemInput {
    public double xSpeed;
    public double ySpeed;
    public double rot;
    public Rotation2d gyroAngle;
    public Pose2d pose;
    
    @Override
    public void fromLog(LogTable table) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void toLog(LogTable table) {
        // TODO Auto-generated method stub
        
    }
}
