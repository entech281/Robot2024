package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import entech.util.EntechJoystick;
import frc.robot.RobotConstants;
import frc.robot.OI.UserPolicy;
import frc.robot.subsystems.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommand {
    private static final double MAX_SPEED_PERCENT = 1;

    private final DriveSubsystem drive;
    private final EntechJoystick joystick;
    private final Supplier<Rotation2d> gyroYaw;

    public DriveCommand(DriveSubsystem drive, EntechJoystick joystick, Supplier<Rotation2d> gyroYaw) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;
        this.gyroYaw = gyroYaw;
    }

    @Override
    public void end(boolean interrupted) {
        DriveInput stop = new DriveInput();

        stop.gyroAngle = gyroYaw.get();
        stop.rot = 0;
        stop.xSpeed= 0;
        stop.ySpeed = 0;

        drive.updateInputs(stop);
    }

    @Override
    public void execute() {
        double xRaw = joystick.getX();
        double yRaw = joystick.getY();
        double rotRaw = joystick.getZ();

        double xConstrained = MathUtil.applyDeadband(MathUtil.clamp(xRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        double yConstrained = MathUtil.applyDeadband(MathUtil.clamp(yRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        double rotConstrained = MathUtil.applyDeadband(
                MathUtil.clamp(rotRaw, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);

        DriveInput input = new DriveInput();

        input.xSpeed = -Math.copySign(xConstrained * xConstrained, xConstrained);
        input.ySpeed = -Math.copySign(yConstrained * yConstrained, yConstrained);
        input.rot = -Math.copySign(rotConstrained * rotConstrained, rotConstrained);
        input.gyroAngle = gyroYaw.get();

        if (UserPolicy.xLocked) {
            drive.setX();
            return;
        }

        input.rot = UserPolicy.twistable ? -Math.copySign(rotConstrained * rotConstrained, rotConstrained) : 0.0;

        drive.updateInputs(input);
    }

    @Override
    public void initialize() {
        DriveInput initial = new DriveInput();

        initial.gyroAngle = gyroYaw.get();
        initial.rot = 0;
        initial.xSpeed= 0;
        initial.ySpeed = 0;

        drive.updateInputs(initial);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
