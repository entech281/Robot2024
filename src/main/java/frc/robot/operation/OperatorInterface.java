package frc.robot.operation;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.entech.subsystems.EntechSubsystem;
import frc.entech.util.DriverControllerUtils;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.AlignNoteToggleCommand;
import frc.robot.commands.ClimbJogLeftCommand;
import frc.robot.commands.ClimbJogRightCommand;
import frc.robot.commands.ClimbJogStopCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.EjectNoteCommand;
import frc.robot.commands.FeedShooterCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.IntakeNoteCommand;
import frc.robot.commands.LowerClimbCommand;
import frc.robot.commands.PivotUpCommand;
import frc.robot.commands.PrepareToShootCommand;
import frc.robot.commands.RaiseClimbCommand;
import frc.robot.commands.ResetOdometryCommand;
import frc.robot.commands.RunTestCommand;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XDriveCommand;
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
  private CommandJoystick joystickController;
  private CommandXboxController xboxController;
  private CommandXboxController tuningController;
  private final edu.wpi.first.wpilibj2.command.button.CommandJoystick operatorPanel =
      new CommandJoystick(RobotConstants.PORTS.CONTROLLER.PANEL);

  private final CommandFactory commandFactory;
  private final SubsystemManager subsystemManager;
  private final OdometryProcessor odometry;

  private final SendableChooser<Command> testChooser;

  public OperatorInterface(CommandFactory commandFactory, SubsystemManager subsystemManager,
      OdometryProcessor odometry) {
    this.commandFactory = commandFactory;
    this.subsystemManager = subsystemManager;
    this.odometry = odometry;
    this.testChooser = getTestCommandChooser();
  }

  public void create() {
    xboxController = new CommandXboxController(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
    enableXboxBindings();
    if (DriverControllerUtils.controllerIsPresent(RobotConstants.PORTS.CONTROLLER.TEST_JOYSTICK)) {
      joystickController = new CommandJoystick(RobotConstants.PORTS.CONTROLLER.TEST_JOYSTICK);
      enableJoystickBindings();
    }

    if (DriverControllerUtils
        .controllerIsPresent(RobotConstants.PORTS.CONTROLLER.TUNING_CONTROLLER)) {
      tuningController =
          new CommandXboxController(RobotConstants.PORTS.CONTROLLER.TUNING_CONTROLLER);
    }

    operatorBindings();
  }

  public void enableTuningControllerBindings() {
    tuningController.a().onTrue(new RunCommand(() -> {
      RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.setWheelDiameter(
          RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getWheelDiameter() - 0.01);
    }, null));
    tuningController.y().onTrue(new RunCommand(() -> {
      RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.setWheelDiameter(
          RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getWheelDiameter() + 0.01);
    }, null));
  }

  public void configureBindings() {
    if (DriverControllerUtils.currentControllerIsXbox()) {
      xboxController = new CommandXboxController(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
      enableXboxBindings();
    } else {
      joystickController = new CommandJoystick(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
      enableJoystickBindings();
    }
  }

  public void enableJoystickBindings() {
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.TWIST)
        .whileTrue(new TwistCommand());
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.GYRO_RESET)
        .onTrue(new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.RUN_TESTS)
        .onTrue(new RunTestCommand(testChooser));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));
    // align to speaker or amp depending on an operator switch

    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.RESET_ODOMETRY)
        .onTrue(new ResetOdometryCommand(odometry));
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_LEFT)
        .whileTrue(new ClimbJogLeftCommand(subsystemManager.getClimbSubsystem()));
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_LEFT)
        .onFalse(new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_RIGHT)
        .onFalse(new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));
    joystickController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_RIGHT)
        .onTrue(new ClimbJogRightCommand(subsystemManager.getClimbSubsystem()));
  }

  public void enableXboxBindings() {
    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.GYRO_RESET)
        .onTrue(new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.NOTE_ALIGN)
        .whileTrue(new AlignNoteToggleCommand());

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.TARGET_AMP)
        .onTrue(commandFactory.getTargetAmpCommand());

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.TARGET_SPEAKER)
        .onTrue(commandFactory.getTargetSpeakerCommand());

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.TARGET_AMP)
        .onFalse(Commands.runOnce(() -> UserPolicy.getInstance().setTargetPose(null)));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.TARGET_SPEAKER)
        .onFalse(Commands.runOnce(() -> UserPolicy.getInstance().setTargetPose(null)));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.FEED_SHOOTER)
        .whileTrue(new FeedShooterCommand(subsystemManager.getTransferSubsystem()));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.DRIVE_X)
        .whileTrue(new XDriveCommand(subsystemManager.getDriveSubsystem()));
    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.RESET_ODOMETRY)
        .onTrue(new ResetOdometryCommand(odometry));
  }

  public void operatorBindings() {

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.SHOOT)
        .whileTrue(new PrepareToShootCommand(subsystemManager.getShooterSubsystem(),
            subsystemManager.getPivotSubsystem(), subsystemManager.getIntakeSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.PIVOT_AMP),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.PIVOT_SPEAKER),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.AUTO_ANGLE),
            xboxController.getHID()));

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.INTAKE)
        .whileTrue(new IntakeNoteCommand(subsystemManager.getIntakeSubsystem(),
            subsystemManager.getTransferSubsystem(), subsystemManager.getShooterSubsystem(),
            subsystemManager.getLedSubsystem()));

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.EJECT)
        .whileTrue(new EjectNoteCommand(subsystemManager.getIntakeSubsystem(),
            subsystemManager.getTransferSubsystem(), subsystemManager.getShooterSubsystem()));


    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.CLIMB)
        .whileTrue(new RaiseClimbCommand(subsystemManager.getClimbSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.CANCEL_CLIMB)))
        .whileFalse(new LowerClimbCommand(subsystemManager.getClimbSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.CANCEL_CLIMB)));

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.RAISE_ARM)
        .whileTrue(new PivotUpCommand(subsystemManager.getPivotSubsystem()));
    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.LOWER_CLIMB_LEFT)
        .whileTrue(new ClimbJogLeftCommand(subsystemManager.getClimbSubsystem()));
    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.LOWER_CLIMB_RIGHT)
        .whileTrue(new ClimbJogRightCommand(subsystemManager.getClimbSubsystem()));
    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.LOWER_CLIMB_LEFT)
        .onFalse(new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));
    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.LOWER_CLIMB_RIGHT)
        .onFalse(new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));

    testChooser.addOption("All tests", getTestCommand());
    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
    SmartDashboard.putData("Test Chooser", testChooser);

    testChooser.addOption("All tests", getTestCommand());

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.RUN_TEST)
        .onTrue(new RunTestCommand(testChooser));
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

    di.setGyroAngle(Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()));
    di.setLatestOdometryPose(odometry.getEstimatedPose());
    di.setKey("initialRaw");

    if (DriverControllerUtils.currentControllerIsXbox()) {
      di.setXSpeed(-this.xboxController.getLeftY());
      di.setYSpeed(-this.xboxController.getLeftX());
      di.setRotation(DriverControllerUtils.getXboxRotation(this.xboxController));
    } else {
      di.setXSpeed(-this.joystickController.getY());
      di.setYSpeed(-this.joystickController.getX());
      di.setRotation(-this.joystickController.getZ());
    }

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
