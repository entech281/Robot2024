package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import entech.commands.EntechCommandBase;
import entech.util.EntechJoystick;
import frc.robot.RobotConstants;
import frc.robot.OI.UserPolicy;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommandBase {
    private static final double MAX_SPEED_PERCENT = 0.8;

    private final DriveSubsystem drive;
    private final EntechJoystick joystick;

    public DriveCommand(DriveSubsystem drive, EntechJoystick joystick) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drive(0, 0, 0, true, true);
    }

    @Override
    public void execute() {
        double xRaw = joystick.getX();
        double yRaw = joystick.getY();
        double rotRaw = joystick.getZ();

        double xConstrained = MathUtil.applyDeadband(MathUtil.clamp(xRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.GAMEPAD.GAMEPAD_AXIS_THRESHOLD);
        double yConstrained = MathUtil.applyDeadband(MathUtil.clamp(yRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.GAMEPAD.GAMEPAD_AXIS_THRESHOLD);
        double rotConstrained = MathUtil.applyDeadband(
                MathUtil.clamp(rotRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.GAMEPAD.GAMEPAD_AXIS_THRESHOLD);

        double xSquared = Math.copySign(xConstrained * xConstrained, xConstrained);
        double ySquared = Math.copySign(yConstrained * yConstrained, yConstrained);
        double rotSquared = Math.copySign(rotConstrained * rotConstrained, rotConstrained);

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
