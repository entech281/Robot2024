package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.TwistCommand;
import frc.robot.commands.XCommand;

@SuppressWarnings("unused")
public final class OperatorInterface {
    private static final EntechJoystick driveJoystick = new EntechJoystick(RobotConstants.Ports.CONTROLLER.JOYSTICK);
    private static final EntechJoystick operatorPanel = new EntechJoystick(RobotConstants.Ports.CONTROLLER.PANEL);

    /**
     * Connects commands to operator panel and joystick.
     * 
     * 
     * @param commandFactory
     * @param subsystemManager
     */
    public static void create(CommandFactory commandFactory, SubsystemManager subsystemManager) {
        driveJoystick.WhilePressed(1, new TwistCommand());
        driveJoystick.WhenPressed(11, commandFactory.gyroResetCommand());
        driveJoystick.WhenPressed(9, new XCommand());
        subsystemManager.getDriveSubsystem()
                .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), driveJoystick));
    }

    private OperatorInterface() {
    }
}