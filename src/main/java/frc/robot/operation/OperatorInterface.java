package frc.robot.operation;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.YesNoSequenceCommand;
import frc.robot.io.DebugInput;
import frc.robot.io.DebugInputSupplier;
import frc.robot.io.DriveInputSupplier;
import frc.robot.io.OperatorInput;
import frc.robot.io.OperatorInputSupplier;
import frc.robot.io.RobotIO;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.drive.DriveInput;

import java.util.Optional;


public class OperatorInterface
    implements DriveInputSupplier, DebugInputSupplier, OperatorInputSupplier {
  private  Optional<EntechJoystick> driveJoystickHolder;
  private  Optional<EntechJoystick> operatorPanelHolder;

  private  Optional<EntechJoystick> debugControllerHolder;
  private final CommandFactory commandFactory;
  private final SubsystemManager subsystemManager;
  private final OdometryProcessor odometry;

  public OperatorInterface(CommandFactory commandFactory, SubsystemManager subsystemManager,
      OdometryProcessor odometry) {
    this.commandFactory = commandFactory;
    this.subsystemManager = subsystemManager;
    this.odometry = odometry;
    operatorPanelHolder = Optional.empty();
    debugControllerHolder = Optional.empty();
    driveJoystickHolder = Optional.empty();
  }

  public void configureBindings(){

    if ( DebugController.isDebugControllerAvailable()){
      enableDebugControlBindings();
    }
    else{
      enableTeleopControlBindings();
    }
  }
  public void enableDebugControlBindings(){
    //here we put self test stuff, based ONLY upon debug controller
    driveJoystickHolder = Optional.empty();
    operatorPanelHolder = Optional.empty();
    debugControllerHolder = DebugController.findDebugController();
    if ( debugControllerHolder.isEmpty() ){
      DriverStation.reportWarning("DebugController Enabled, but no controller found? ",false);
      return;
    }

    EntechJoystick debugJoystick = debugControllerHolder.get();
    Command test1 = Commands.waitSeconds(1);
    Command test2 = Commands.waitSeconds(1);
    Command test3 = Commands.waitSeconds(1);
    Command test4 = Commands.waitSeconds(1);
    Command self_tests = new YesNoSequenceCommand(
        debugJoystick.button(RobotConstants.DEBUG_PANEL.YES_BUTTON),
        debugJoystick.button(RobotConstants.DEBUG_PANEL.NO_BUTTON),
        test1, test2, test3, test4);

    //once started, each command will run until yes or no is pressed
    debugJoystick.button(RobotConstants.DEBUG_PANEL.START_TEST_BUTTON).onTrue(self_tests);

  }
  public void enableTeleopControlBindings() {
    driveJoystickHolder =  Optional.of(new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK));
    operatorPanelHolder = Optional.of(new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL));
    debugControllerHolder = Optional.empty();

    if (driveJoystickHolder.isEmpty()){
       DriverStation.reportWarning("Hey, man: No joystick is plugged in",false);
    }
    EntechJoystick driveJoystick = driveJoystickHolder.get();

    driveJoystick.whilePressed(1, new TwistCommand());
    driveJoystick.whenPressed(11, new GyroReset(subsystemManager.getNavXSubsystem(), odometry));

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

    if ( driveJoystickHolder.isPresent()){
      EntechJoystick driveJoystick = driveJoystickHolder.get();
      di.setXSpeed(-driveJoystick.getY());
      di.setYSpeed(-driveJoystick.getX());
      di.setRotation(-driveJoystick.getZ());
      di.setGyroAngle(Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()));
      di.setLatestOdometryPose(odometry.getEstimatedPose());
      di.setKey("initialRaw");
    }
    RobotIO.processInput(di);
    return di;
  }

  @Override
  public OperatorInput getOperatorInput() {
    OperatorInput oi = new OperatorInput();

    if ( operatorPanelHolder.isPresent()){
       //set values here from it
    }

    RobotIO.processInput(oi);
    return oi;
  }
}
