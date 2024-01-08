package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.RobotContainer;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XCommand;

@SuppressWarnings("unused")
public class OperatorInterface {

        private final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
        private final EntechJoystick operatorPanel = new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);

        public OperatorInterface(CommandFactory commandFactory, RobotContainer robotContainer) {
                driveJoystick.WhilePressed(1, new TwistCommand());
                driveJoystick.WhenPressed(11, commandFactory.gyroResetCommand());
                driveJoystick.WhenPressed(9, new XCommand());
                robotContainer.getDriveSubsystem()
                                .setDefaultCommand(new DriveCommand(robotContainer.getDriveSubsystem(), driveJoystick));
        }
}