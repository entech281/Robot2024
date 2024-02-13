package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import entech.util.EntechJoystick;
import frc.robot.processors.DriveInputProcessor;
import frc.robot.subsystems.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommand {
    private final DriveSubsystem drive;
    private final EntechJoystick joystick;
    private final Supplier<Rotation2d> gyroYaw;
    private final Supplier<Pose2d> odometryPose;
    private final DriveInputProcessor inputProcessor;

    public DriveCommand(DriveSubsystem drive, EntechJoystick joystick, Supplier<Rotation2d> gyroYaw, Supplier<Pose2d> odometryPose) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;
        this.gyroYaw = gyroYaw;
        this.odometryPose = odometryPose;
        this.inputProcessor = new DriveInputProcessor();
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
        DriveInput input = new DriveInput();

        input.ySpeed = -joystick.getX();
        input.xSpeed = -joystick.getY();
        input.rot = -joystick.getZ();

        input.gyroAngle = gyroYaw.get();
        input.pose = odometryPose.get();

        input = inputProcessor.processInput(input);

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
