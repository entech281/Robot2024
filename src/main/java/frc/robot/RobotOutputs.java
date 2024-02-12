package frc.robot;

import frc.robot.subsystems.DriveOutput;
import frc.robot.subsystems.NavXOutput;
import frc.robot.subsystems.VisionOutput;

public class RobotOutputs {
    private static RobotOutputs instance;

    private VisionOutput visionOutput;
    private NavXOutput navXOutput;
    private DriveOutput driveOutput;

    private RobotOutputs() {

    }

    public static RobotOutputs getInstance() {
        if (instance == null) {
            instance = new RobotOutputs();
        }
        return instance;
    }

    public VisionOutput getVisionOutput() {
        return this.visionOutput;
    }

    public void setVisionOutput(VisionOutput visionOutput) {
        this.visionOutput = visionOutput;
    }

    public NavXOutput getNavXOutput() {
        return this.navXOutput;
    }

    public void setNavXOutput(NavXOutput navXOutput) {
        this.navXOutput = navXOutput;
    }

    public DriveOutput getDriveOutput() {
        return this.driveOutput;
    }

    public void setDriveOutput(DriveOutput driveOutput) {
        this.driveOutput = driveOutput;
    }
}
