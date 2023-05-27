package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.VisionSubsystem;

public class SubsystemInterface {
    private DriveSubsystem driveSubsys;
    private NavXSubSystem navXSubSys;
    private VisionSubsystem visionSubSys;

    public void initialize() {
        driveSubsys = new DriveSubsystem();
        navXSubSys = new NavXSubSystem();
        visionSubSys = new VisionSubsystem();
        driveSubsys.initialize();
        navXSubSys.initialize();
        visionSubSys.initialize();
    }

    public DriveSubsystem getDriveSubsys() {
        return this.driveSubsys;
    }

    public NavXSubSystem getNavXSubSys() {
        return this.navXSubSys;
    }

    public VisionSubsystem getVisionSubSys() {
        return this.visionSubSys;
    }
}
