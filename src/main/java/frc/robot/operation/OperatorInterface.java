package frc.robot.operation;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import entech.subsystems.EntechSubsystem;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.IntakeNoteCommand;
import frc.robot.commands.PivotNudgeCommand;
import frc.robot.commands.PositionPivotCommand;
import frc.robot.commands.RunTestCommand;
import frc.robot.commands.ShootSpeakerCommand;
import frc.robot.commands.TwistCommand;
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
      new EntechJoystick(RobotConstants.PORTS.CONTROLLER.JOYSTICK);
  private final EntechJoystick operatorPanel =
      new EntechJoystick(RobotConstants.PORTS.CONTROLLER.PANEL);

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
    driveJoystick.whilePressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.TWIST, new TwistCommand());
    driveJoystick.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.GYRO_RESET,
        new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));
    driveJoystick.whilePressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.INTAKE,
        new IntakeNoteCommand(subsystemManager.getIntakeSubsystem(),
            subsystemManager.getTransferSubsystem()));
    driveJoystick.whilePressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.ALIGN_SPEAKER_AMP,
        new DoNothing()); // align to speaker or amp depending on an operator switch
    // driveJoystick.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.PIVOT,
    // new PositionPivotCommand(subsystemManager.getPivotSubsystem()));

    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
    SendableChooser<Command> testChooser = getTestCommandChooser();
    SmartDashboard.putData("Test Chooser", testChooser);

    testChooser.addOption("All tests", getTestCommand());

    driveJoystick.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS.RUN_TESTS,
        new RunTestCommand(testChooser));

    subsystemManager.getPivotSubsystem().setDefaultCommand(new PivotNudgeCommand(
        subsystemManager.getPivotSubsystem(), driveJoystick.getHID()::getPOV));
  }

  public void operatorBindings() {
    // operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.SHOOT_AMP)
    // .whileTrue(new DoNothing());
    // operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.SHOOT_SPEAKER)
    // .whileTrue(new DoNothing());
    operatorPanel.whenPressed(RobotConstants.OPERATOR_PANEL.SWITCHES.INTAKE, new IntakeNoteCommand(
        subsystemManager.getIntakeSubsystem(), subsystemManager.getTransferSubsystem()));
    operatorPanel.whilePressed(6, new ShootSpeakerCommand(subsystemManager.getShooterSubsystem(),
        subsystemManager.getPivotSubsystem(), subsystemManager.getTransferSubsystem()));
    // run intake and transfer backwards and eject note
    // operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.ADVANCE_CLIMB)
    // .whileTrue(new DoNothing()); // advance to next stage of climb
    // operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.REVERSE_CLIMB)
    // .onTrue(new DoNothing()); // revert to last state of climb
    // operatorPanel.whileSwitch(RobotConstants.OPERATOR_PANEL.SWITCHES.ALIGN_SPEAKER_AMP,
    // new SetTargetCommand(new Pose2d(0.0, 5.6, new Rotation2d())),
    // new SetTargetCommand(new Pose2d(1.81, 8.2, new Rotation2d())));
  }

  private SendableChooser<Command> getTestCommandChooser() {
    SendableChooser<Command> testCommandChooser = new SendableChooser<>();
    for (EntechSubsystem<?, ?> subsystem : subsystemManager.getSubsystemList()) {
      testCommandChooser.addOption(subsystem.getName(), subsystem.getTestCommand());
    }
    return testCommandChooser;
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

  public Command getTestCommand() {
    SequentialCommandGroup allTests = new SequentialCommandGroup();
    for (EntechSubsystem<?, ?> subsystem : subsystemManager.getSubsystemList()) {
      if (subsystem.isEnabled()) {
        addSubsystemTest(allTests, subsystem);
      }
    }
    allTests.addCommands(Commands.runOnce(() -> Logger
        .recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Tests.")));
    return allTests;
  }

  private static void addSubsystemTest(SequentialCommandGroup group,
      EntechSubsystem<?, ?> subsystem) {

    group.addCommands(
        Commands.runOnce(() -> Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
            String.format("%s: Start", subsystem.getName()))),
        subsystem.getTestCommand(),
        Commands.runOnce(() -> Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
            String.format("%s: Finished", subsystem.getName()))));
  }
}
