package frc.robot.io;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotConstants;
import frc.robot.SubsystemInterface;

public final class ShuffleboardInterface {
    private final static ShuffleboardTab DEBUG = Shuffleboard.getTab(RobotConstants.Shuffleboard.DEBUG);
    // private final static ShuffleboardTab DRIVE =
    // Shuffleboard.getTab(RobotConstants.Shuffleboard.DRIVE);

    public static void addSubsystems(SubsystemInterface si) {
        DEBUG.add(si.getDriveSubsys());
    }
}
