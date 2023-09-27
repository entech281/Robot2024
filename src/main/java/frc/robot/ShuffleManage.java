package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleManage {
    private final SubsystemInterface sys;

    private static final ShuffleboardTab DEBUG = Shuffleboard.getTab("Debug");

    public ShuffleManage(SubsystemInterface sys) {
        this.sys = sys;
    }

    public void initialize() {
        DEBUG.add(sys.getDriveSubsys());
        DEBUG.add(sys.getNavXSubSys());
    }
}
