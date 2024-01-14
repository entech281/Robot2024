package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;

import entech.commands.AutoSequence;
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

    public AutoSequence getAutoCommand() {
        AutoSequence auto = new AutoSequence(RobotConstants.AUTONOMOUS.StartingLocations.FRONT_OF_SUBWOOFER);
        auto.addCommands(new PathPlannerAuto("Test"));
        return auto;
    }
}
