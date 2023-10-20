// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants.DrivetrainConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    public static final double GAMEPAD_AXIS_THRESHOLD = 0.2;

    public enum Autonomous {

    }

    private Autonomous auto = null;

    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final ElbowSubsystem elbowSubsystem = new ElbowSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final GripperSubsystem gripperSubsystem = new GripperSubsystem();

    private final Field2d field = new Field2d(); // a representation of the field

    // The driver's controller
    // CommandXboxController driverGamepad = new
    // CommandXboxController(Ports.USB.GAMEPAD);
    Joystick driverGamepad = new Joystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        driveSubsystem.initialize();
        visionSubsystem.initialize();
        armSubsystem.initialize();
        elbowSubsystem.initialize();
        gripperSubsystem.initialize();

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        switch (auto) {
            default:
                return null;
        }
    }

    public TrajectoryConfig createTrajectoryConfig() {
        TrajectoryConfig config = new TrajectoryConfig(
                RobotConstants.AUTONOMOUS.MAX_SPEED_METERS_PER_SECOND,
                RobotConstants.AUTONOMOUS.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                .setKinematics(DrivetrainConstants.DRIVE_KINEMATICS);

        return config;
    }

    public Trajectory createExampleTrajectory(TrajectoryConfig config) {
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                new Pose2d(3, 0, new Rotation2d(0)),
                config);

        return exampleTrajectory;
    }

    public Field2d getField() {
        return field;
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public ArmSubsystem getArmSubsystem() {
        return armSubsystem;
    }

    public ElbowSubsystem getElbowSubsystem() {
        return elbowSubsystem;
    }

    public GripperSubsystem getGripperSubsystem() {
        return gripperSubsystem;
    }

    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }
}
