// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.OI.OperatorInterface;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    private void initAdvantageKit() {
        Logger.getInstance().recordMetadata("ProjectName", "MyProject");
        if (isReal()) {
            Logger.getInstance().addDataReceiver(new WPILOGWriter("/media/sda1/"));
            Logger.getInstance().addDataReceiver(new NT4Publisher());
        } else {
            setUseTiming(false);
            String logPath = LogFileUtil.findReplayLog();
            Logger.getInstance().setReplaySource(new WPILOGReader(logPath));
            Logger.getInstance().addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        }
        Logger logger = Logger.getInstance();
        logger.recordMetadata("RuntimeType", getRuntimeType().toString());
        logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
        logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
        logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
        logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
        logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);

        Logger.getInstance().start();
    }

    @Override
    public void robotInit() {
        initAdvantageKit();

        m_robotContainer = new RobotContainer();
        new OperatorInterface(null, m_robotContainer.getDriveSubsystem());

        SmartDashboard.putData("Swerve Odometry", m_robotContainer.getField());
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items
     * like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {

        updateToSmartDash();
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {

        updateToSmartDash();
    }

    @Override
    public void teleopInit() {
        Compressor c = new Compressor(PneumaticsModuleType.CTREPCM);
        c.enableDigital();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {

        updateToSmartDash();
    }

    public void updateToSmartDash() {
        // SmartDashboard.putNumber("FrontLeftDrivingEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontLeftModule().getDrivingEncoder().getPosition());
        // SmartDashboard.putNumber("FrontLeftTurningEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningEncoder().getPosition());

        // SmartDashboard.putNumber("RearLeftDrivingEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearLeftModule().getDrivingEncoder().getPosition());
        // SmartDashboard.putNumber("RearLeftTurningEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearLeftModule().getTurningEncoder().getPosition());

        // SmartDashboard.putNumber("FrontRightDrivingEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontRightModule().getDrivingEncoder().getPosition());
        // SmartDashboard.putNumber("FrontRightTurningEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontRightModule().getTurningEncoder().getPosition());

        // SmartDashboard.putNumber("RearRightDrivingEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearRightModule().getDrivingEncoder().getPosition());
        // SmartDashboard.putNumber("RearRightTurningEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearRightModule().getTurningEncoder().getPosition());

        // SmartDashboard.putNumber("FrontLeftTurningAbsoluteEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getPosition());
        // SmartDashboard.putNumber("RearLeftTurningAbsoluteEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getPosition());
        // SmartDashboard.putNumber("FrontRightTurningAbsoluteEncoderPosition",
        // m_robotContainer.getDrivetrain().getFrontRightModule().getTurningAbsoluteEncoder().getPosition());
        // SmartDashboard.putNumber("RearRightTurningAbsoluteEncoderPosition",
        // m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getPosition());

        // SmartDashboard.putNumber("FrontLeftTurningAbsoluteEncoderVirtualPosition",
        // m_robotContainer.getDrivetrain().getFrontLeftModule().getTurningAbsoluteEncoder().getVirtualPosition());
        // SmartDashboard.putNumber("RearLeftTurningAbsoluteEncoderVirtualPosition",
        // m_robotContainer.getDrivetrain().getRearLeftModule().getTurningAbsoluteEncoder().getVirtualPosition());
        // SmartDashboard.putNumber("FrontRightTurningAbsoluteEncoderVirtualPosition",
        // m_robotContainer.getDrivetrain()
        // .getFrontRightModule().getTurningAbsoluteEncoder().getVirtualPosition());
        // SmartDashboard.putNumber("RearRightTurningAbsoluteEncoderVirtualPosition",
        // m_robotContainer.getDrivetrain().getRearRightModule().getTurningAbsoluteEncoder().getVirtualPosition());

        // SmartDashboard.putNumber("FrontLeftTurningDesiredState",
        // m_robotContainer.getDrivetrain().getFrontLeftModule().getDesiredState().angle.getRadians());
        // SmartDashboard.putNumber("RearLeftTurningDesiredState",
        // m_robotContainer.getDrivetrain().getRearLeftModule().getDesiredState().angle.getRadians());
        // SmartDashboard.putNumber("FrontRightTurningDesiredState",
        // m_robotContainer.getDrivetrain().getFrontRightModule().getDesiredState().angle.getRadians());
        // SmartDashboard.putNumber("RearRightTurningDesiredState",
        // m_robotContainer.getDrivetrain().getRearRightModule().getDesiredState().angle.getRadians());

        /* Display 6-axis Processed Angle Data */
        // SmartDashboard.putBoolean("IMU_Connected",
        // m_robotContainer.getDrivetrain().getImu().isConnected());
        // SmartDashboard.putBoolean("IMU_IsCalibrating",
        // m_robotContainer.getDrivetrain().getImu().isCalibrating());
        // SmartDashboard.putNumber("IMU_Yaw",
        // m_robotContainer.getDrivetrain().getImu().getYaw());
        // SmartDashboard.putNumber("IMU_Pitch",
        // m_robotContainer.getDrivetrain().getImu().getPitch());
        // SmartDashboard.putNumber("IMU_Roll",
        // m_robotContainer.getDrivetrain().getImu().getRoll());

        // m_robotContainer.getField().setRobotPose(m_robotContainer.getDrivetrain().getPose());
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
