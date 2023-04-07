package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

public class SubsystemInterface {
    private DriveSubsystem driveSubsys;
    private NavXSubSystem navXSubSys;
    public void initialize() {
        driveSubsys = new DriveSubsystem();
        navXSubSys = new NavXSubSystem();
    }

    public DriveSubsystem getDriveSubsys() {
        return this.driveSubsys;
    }

    public NavXSubSystem getNavXSubSys() {
        return this.navXSubSys;
    }
}
