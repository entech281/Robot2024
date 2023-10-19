package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
import frc.robot.commands.HomeLimbCommand;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;

public class OperatorInterface {

    private final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
    // private JoystickDriveInputSupplier hidJoystickDriveInputSupplier;

    public OperatorInterface(final CommandFactory cf, DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem,
            ElbowSubsystem elbowSubsystem) {
        /*
         * this.commandFactory = cf;
         * this.driveStick = new
         * CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
         * this.operatorPanel = new
         * CommandJoystick(RobotConstants.JOYSTICKS.OPERATOR_PANEL);
         * this.hidJoystickDriveInputSupplier = new
         * JoystickDriveInputSupplier(driveStick.getHID());
         * 
         * setupButtons();
         * }
         * 
         * private void setupButtons() {
         * driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
         * .onTrue(commandFactory.setDriverRotationEnableCommand(true))
         * .onFalse(commandFactory.setDriverRotationEnableCommand(false));
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.AUTO_ALIGN_DRIVE)
         * .whileTrue(commandFactory.alignHorizontalToTag(hidJoystickDriveInputSupplier)
         * );
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.SNAP_YAW)
         * .onTrue(commandFactory.getYawToNearestPerpendicular());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.AUTO_YAW_TOGGLE)
         * .onTrue(commandFactory.autoGroundPickupPositionCube())
         * .onFalse(commandFactory.telefrogGrabCommand());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.AUTO_BALANCE_FORWARD)
         * .whileTrue(commandFactory.autoDriveBalanceOnly(RobotConstants.
         * BALANCE_PARAMETERS.CHARGESTATION_APPROACH_SPEED,true));
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.DEPLOY_BRAKE)
         * .onTrue(commandFactory.deployBrakeCommand())
         * .onFalse(commandFactory.retractBrakeCommand());
         * 
         * // removed at drive team request
         * // driveStick.button(RobotConstants.DRIVER_STICK.BRAKE_COAST)
         * // .onTrue(commandFactory.toggleBrakeModeCommand());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
         * .onTrue(commandFactory.toggleFieldAbsoluteCommand())
         * .onFalse(commandFactory.toggleFieldAbsoluteCommand());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.ZERO_GYRO_ANGLE)
         * .onTrue(commandFactory.getZeroGyro());
         * 
         * driveStick.pov(RobotConstants.DRIVER_STICK.POV.RIGHT)
         * .whileTrue(commandFactory.nudgeRightCommand());
         * 
         * driveStick.pov(RobotConstants.DRIVER_STICK.POV.LEFT)
         * .whileTrue(commandFactory.nudgeLeftCommand());
         * 
         * driveStick.pov(RobotConstants.DRIVER_STICK.POV.FORWARD)
         * .whileTrue(commandFactory.nudgeForwardCommand());
         * 
         * driveStick.pov(RobotConstants.DRIVER_STICK.POV.BACKWARD)
         * .whileTrue(commandFactory.nudgeBackwardCommand());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.NUDGE_YAW_LEFT)
         * .onTrue(commandFactory.nudgeYawLeftCommand());
         * 
         * driveStick.button(RobotConstants.DRIVER_STICK.NUDGE_YAW_RIGHT)
         * .onTrue(commandFactory.nudgeYawRightCommand());
         * 
         * // ******* Operator Panel *******
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.GRIPPER)
         * .onTrue(commandFactory.toggleGripperCommand());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_UP)
         * .whileTrue(commandFactory.nudgeElbowUpCommand());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_DOWN)
         * .whileTrue(commandFactory.nudgeElbowDownCommand());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_IN)
         * .whileTrue(commandFactory.nudgeArmBackwardCommand());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_OUT)
         * .whileTrue(commandFactory.nudgeArmForwardCommand());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_OFF)
         * .onTrue(commandFactory.dialCarryPosition());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_LOAD)
         * .onTrue(commandFactory.dialLoadPosition());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_HIGH)
         * .onTrue(commandFactory.dialHighPosition());
         * 
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_MIDDLE)
         * .onTrue(commandFactory.dialMiddlePosition());
         * 
         * operatorPanel.button(RobotConstants.OPERATOR_PANEL.AUTO)
         * .whileTrue(commandFactory.scoreHighCommand());
         * //.onTrue(commandFactory.armPositionFullExtension())
         * //.onFalse(commandFactory.armPositionHome());
         * 
         * // ******* Operator Joytick *******
         * /**
         * operatorStick.button(RobotConstants.OPERATOR_STICK.GRIPPER)
         * .onTrue(commandFactory.openGripperCommand())
         * .onFalse(commandFactory.closeGripperCommand());
         * 
         * operatorStick.button(RobotConstants.OPERATOR_STICK.HOME_ELBOW)
         * .onTrue(commandFactory.homeElbowCommand());
         * 
         * operatorStick.button(RobotConstants.OPERATOR_STICK.HOME_TELESCOPE)
         * .onTrue(commandFactory.homeTelescopeCommand());
         * 
         * operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.UP)
         * .whileTrue(commandFactory.nudgeElbowUpCommand());
         * 
         * operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.DOWN)
         * .whileTrue(commandFactory.nudgeElbowDownCommand());
         * 
         * operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.IN)
         * .whileTrue(commandFactory.nudgeArmBackwardCommand());
         * 
         * operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.OUT)
         * .whileTrue(commandFactory.nudgeArmForwardCommand());
         */

        driveJoystick.WhilePressed(1, new TwistCommand());
        driveJoystick.WhenPressed(11, new GyroReset(driveSubsystem));
        driveJoystick.WhenPressed(9, new XCommand());
        driveJoystick.WhenPressed(7, new HomeLimbCommand(elbowSubsystem, armSubsystem));
        driveSubsystem.setDefaultCommand(new DriveCommand(driveSubsystem, driveJoystick));
    }
}