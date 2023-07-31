package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class SubsystemInterface {
    private DriveSubsystem driveSubsys;
    private VisionSubsystem visionSubSys;

    public void initialize() {
        driveSubsys = new DriveSubsystem();
        visionSubSys = new VisionSubsystem();
        driveSubsys.initialize();
        visionSubSys.initialize();
    }

    public DriveSubsystem getDriveSubsys() {
        return this.driveSubsys;
    }

    public VisionSubsystem getVisionSubSys() {
        return this.visionSubSys;
    }
}
