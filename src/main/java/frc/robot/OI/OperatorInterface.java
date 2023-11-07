package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.RobotContainer;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XCommand;

public class OperatorInterface {

        private final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
        private final EntechJoystick operatorPanel = new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);

        public OperatorInterface(CommandFactory commandFactory, RobotContainer robotContainer) {
                operatorPanel.button(RobotConstants.OPERATOR_PANEL.GRIPPER)
                                .onTrue(commandFactory.toggleGripperCommand());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_UP)
                                .whileTrue(commandFactory.nudgeElbowUpCommand());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_DOWN)
                                .whileTrue(commandFactory.nudgeElbowDownCommand());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_IN)
                                .whileTrue(commandFactory.nudgeArmBackwardCommand());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_OUT)
                                .whileTrue(commandFactory.nudgeArmForwardCommand());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_OFF)
                                .onTrue(commandFactory.dialCarryPosition());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_LOAD)
                                .onTrue(commandFactory.dialLoadPosition());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_HIGH)
                                .onTrue(commandFactory.dialHighPosition());

                operatorPanel.button(RobotConstants.OPERATOR_PANEL.ARM_MIDDLE)
                                .onTrue(commandFactory.dialMiddlePosition());
                operatorPanel.button(RobotConstants.OPERATOR_PANEL.AUTO)
                                .onTrue(commandFactory.scoreHighCommand());

                driveJoystick.WhilePressed(1, new TwistCommand());
                driveJoystick.WhenPressed(11, commandFactory.gyroResetCommand());
                driveJoystick.WhenPressed(9, new XCommand());
                driveJoystick.WhenPressed(7, commandFactory.homeLimbCommand());
                robotContainer.getDriveSubsystem()
                                .setDefaultCommand(new DriveCommand(robotContainer.getDriveSubsystem(), driveJoystick));
        }
}