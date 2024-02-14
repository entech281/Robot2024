package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import entech.commands.EntechCommand;
import frc.robot.OI.UserPolicy;
import frc.robot.RobotConstants;
import frc.robot.io.DriveInputSupplier;
import frc.robot.subsystems.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommand {
    private static final double MAX_SPEED_PERCENT = 1;

    private final DriveSubsystem drive;
    private final DriveInputSupplier driveInputSource;

    public DriveCommand(DriveSubsystem drive, DriveInputSupplier driveInputSource) {
        super(drive);
        this.drive = drive;
        this.driveInputSource = driveInputSource;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drive(0, 0, 0, true, true);
    }

    @Override
    public void execute() {
        DriveInput driveInput = driveInputSource.getDriveInput();
        double xRaw = driveInput.forward;
        double yRaw = driveInput.right;
        double rotRaw = driveInput.rotation;

        double xConstrained = MathUtil.applyDeadband(MathUtil.clamp(xRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
            RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        double yConstrained = MathUtil.applyDeadband(MathUtil.clamp(yRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
            RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        double rotConstrained = MathUtil.applyDeadband(
            MathUtil.clamp(rotRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
            RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);

        double xSquared = Math.copySign(xConstrained * xConstrained, xConstrained);
        double ySquared = Math.copySign(yConstrained * yConstrained, yConstrained);
        double rotSquared = Math.copySign(rotConstrained * rotConstrained, rotConstrained);

        if (UserPolicy.xLocked) {
            drive.setX();
            return;
        }

        if (UserPolicy.twistable) {
            drive.drive(-ySquared, -xSquared, -rotSquared, true, true);
        } else {
            drive.drive(-ySquared, -xSquared, 0, true, true);
        }
    }

    @Override
    public void initialize() {
        drive.drive(0, 0, 0, true, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
