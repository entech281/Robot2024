package frc.robot.operation;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import entech.subsystems.EntechSubsystem;
import entech.util.DriverControllerUtils;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.AlignNoteToggleCommand;
import frc.robot.commands.ClimbJogLeftCommand;
import frc.robot.commands.ClimbJogRightCommand;
import frc.robot.commands.ClimbJogStopCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.EjectNoteCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.IntakeNoteCommand;
import frc.robot.commands.LowerClimbCommand;
import frc.robot.commands.PivotUpCommand;
import frc.robot.commands.RaiseClimbCommand;
import frc.robot.commands.ResetOdometryCommand;
import frc.robot.commands.RunTestCommand;
import frc.robot.commands.ShootCommand;
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
  private EntechJoystick joystickController;
  private CommandXboxController xboxController;
  private final EntechJoystick operatorPanel =
      new EntechJoystick(RobotConstants.PORTS.CONTROLLER.PANEL);

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
      joystickController = new EntechJoystick(RobotConstants.PORTS.CONTROLLER.TEST_JOYSTICK);
      enableJoystickBindings();
    }
    operatorBindings();
  }

  public void configureBindings() {
    if (DriverControllerUtils.currentControllerIsXbox()) {
      xboxController = new CommandXboxController(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
      enableXboxBindings();
    } else {
      joystickController = new EntechJoystick(RobotConstants.PORTS.CONTROLLER.DRIVER_CONTROLLER);
      enableJoystickBindings();
    }
  }


  public void enableJoystickBindings() {
    joystickController.whilePressed(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.TWIST,
        new TwistCommand());
    joystickController.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.GYRO_RESET,
        new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    joystickController.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.RUN_TESTS,
        new RunTestCommand(testChooser));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));
    // align to speaker or amp depending on an operator switch

    joystickController.whenPressed(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.RESET_ODOMETRY,
        new ResetOdometryCommand(odometry));
    joystickController.whilePressed(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_LEFT,
        new ClimbJogLeftCommand(subsystemManager.getClimbSubsystem()));
    joystickController.whenReleased(RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_LEFT,
        new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));
    joystickController.whenReleased(
        RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_RIGHT,
        new ClimbJogStopCommand(subsystemManager.getClimbSubsystem()));
    joystickController.whilePressed(
        RobotConstants.PORTS.CONTROLLER.BUTTONS_JOYSTICK.CLIMB_JOG_RIGHT,
        new ClimbJogRightCommand(subsystemManager.getClimbSubsystem()));

    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
    SmartDashboard.putData("Test Chooser", testChooser);

    testChooser.addOption("All tests", getTestCommand());
  }

  public void enableXboxBindings() {
    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.GYRO_RESET)
        .onTrue(new GyroReset(subsystemManager.getNavXSubsystem(), odometry));
    // driveJoystick.whenPressed(RobotConstants.Ports.CONTROLLER.BUTTONS.GYRO_RESET,new
    // GyroReset(subsystemManager.getNavXSubsystem(), odometry));

    subsystemManager.getDriveSubsystem()
        .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), this));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.FULL_PIVOT)
        .whileTrue(new PivotUpCommand(subsystemManager.getPivotSubsystem()));

    xboxController.button(RobotConstants.PORTS.CONTROLLER.BUTTONS_XBOX.NOTE_ALIGN)
        .whileTrue(new AlignNoteToggleCommand());

    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
    SendableChooser<Command> testChooser = getTestCommandChooser();
    SmartDashboard.putData("Test Chooser", testChooser);

    testChooser.addOption("All tests", getTestCommand());

  }

  public void operatorBindings() {

    operatorPanel.whilePressed(RobotConstants.OPERATOR_PANEL.BUTTONS.SHOOT,
        new ShootCommand(subsystemManager.getShooterSubsystem(),
            subsystemManager.getPivotSubsystem(), subsystemManager.getTransferSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.PIVOT_AMP),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.PIVOT_SPEAKER)));

    operatorPanel.whilePressed(RobotConstants.OPERATOR_PANEL.BUTTONS.INTAKE,
        new IntakeNoteCommand(subsystemManager.getIntakeSubsystem(),
            subsystemManager.getTransferSubsystem(), subsystemManager.getLedSubsystem()));
    operatorPanel.whilePressed(RobotConstants.OPERATOR_PANEL.BUTTONS.EJECT,
        new EjectNoteCommand(subsystemManager.getIntakeSubsystem(),
            subsystemManager.getTransferSubsystem(), subsystemManager.getShooterSubsystem()));

    operatorPanel.button(RobotConstants.OPERATOR_PANEL.BUTTONS.CLIMB)
        .whileTrue(new RaiseClimbCommand(subsystemManager.getClimbSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.CANCEL_CLIMB)))
        .whileFalse(new LowerClimbCommand(subsystemManager.getClimbSubsystem(),
            operatorPanel.button(RobotConstants.OPERATOR_PANEL.SWITCHES.CANCEL_CLIMB)));
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
