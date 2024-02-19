package frc.robot.OI;

import edu.wpi.first.math.geometry.Rotation2d;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.TwistCommand;
import frc.robot.io.*;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.drive.DriveInput;


public class OperatorInterface
    implements DriveInputSupplier, DebugInputSupplier, OperatorInputSupplier {
  private final EntechJoystick driveJoystick =
      new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
  private final EntechJoystick operatorPanel =
      new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);

  private final CommandFactory commandFactory;
  private final SubsystemManager subsystemManager;
  private final OdometryProcessor odometry;

  public OperatorInterface(CommandFactory commandFactory, SubsystemManager subsystemManager,
      OdometryProcessor odometry) {
    this.commandFactory = commandFactory;
    this.subsystemManager = subsystemManager;
    this.odometry = odometry;
  }

  public void create() {
    driveJoystick.WhilePressed(1, new TwistCommand());
    driveJoystick.WhenPressed(11, new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));

  }

  /*
   * These force commands to accept inputs, not raw joysticks and stuff also here we log any inputs
   * handed to consumers, so they dont have to
   */
  @Override
  public DebugInput getDebugInput() {
    DebugInput di = new DebugInput();
    RobotIO.processInput(di);
    return di;
  }

  @Override
  public DriveInput getDriveInput() {
    DriveInput di = new DriveInput();

    di.xSpeed = -driveJoystick.getY();
    di.ySpeed = -driveJoystick.getX();
    di.rot = -driveJoystick.getZ();
    di.gyroAngle = Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().yaw);
    di.latestOdometryPose = odometry.getEstimatedPose();
    di.key = "initialRaw";

    RobotIO.processInput(di);
    return di;
  }

  @Override
  public OperatorInput getOperatorInput() {
    OperatorInput oi = new OperatorInput();
    RobotIO.processInput(oi);
    return oi;
  }
}
