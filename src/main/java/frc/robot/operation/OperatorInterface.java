package frc.robot.operation;

import edu.wpi.first.math.geometry.Rotation2d;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.io.DebugInput;
import frc.robot.io.DebugInputSupplier;
import frc.robot.io.DriveInputSupplier;
import frc.robot.io.OperatorInput;
import frc.robot.io.OperatorInputSupplier;
import frc.robot.io.RobotIO;
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
    driverBindings();
    operatorBindings();
  }

  public void driverBindings() {
    driveJoystick.whilePressed(1, new TwistCommand());
    driveJoystick.whenPressed(11, new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));
    driveJoystick.whilePressed(2, new IntakeCommand(subsystemManager.getIntakeSubsystem(), subsystemManager.getTransferSubsystem()));
    driveJoystick.whilePressed(3, new DoNothing()); // align to speaker or amp depending on an operator switch
  }

  public void operatorBindings() {
    operatorPanel.button(1).whileTrue(new DoNothing()); // shoot speaker
    operatorPanel.button(2).whileTrue(new DoNothing()); // shoot amp
    operatorPanel.whileSwitch(3, new IntakeCommand(subsystemManager.getIntakeSubsystem(), subsystemManager.getTransferSubsystem()), new DoNothing()); // run intake and transfer backwards and eject note
    operatorPanel.button(4).whileTrue(new DoNothing()); // advance to next stage of climb
    operatorPanel.button(5).onTrue(new DoNothing()); // revert to last state of climb
    operatorPanel.whileSwitch(6, new DoNothing(), new DoNothing());
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

    di.setXSpeed(-driveJoystick.getY());
    di.setYSpeed(-driveJoystick.getX());
    di.setRotation(-driveJoystick.getZ());
    di.setGyroAngle(Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()));
    di.setLatestOdometryPose(odometry.getEstimatedPose());
    di.setKey("initialRaw");

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
