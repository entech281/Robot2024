package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import entech.commands.AutoSequence;
import entech.commands.EntechCommand;
import frc.robot.commands.GyroReset;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

@SuppressWarnings("unused")
public class CommandFactory {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;

    public CommandFactory(SubsystemManager subsystemManager) {
        this.driveSubsystem = subsystemManager.getDriveSubsystem();
        this.visionSubsystem = subsystemManager.getVisionSubsystem();
    }

    public EntechCommand gyroResetCommand() {
        return new GyroReset(driveSubsystem);
    }

    public AutoSequence getAutoCommand() {
        Translation2d initialTranslation = new Translation2d(2, 7);
        Rotation2d initialRotation = Rotation2d.fromDegrees(180);
        AutoSequence auto = new AutoSequence(new Pose2d(initialTranslation, initialRotation));
        return auto;
    }
}
