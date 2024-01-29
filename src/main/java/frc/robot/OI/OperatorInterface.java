package frc.robot.OI;

import entech.util.EntechJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.SubsystemManager;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroReset;
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
        driveJoystick.whilePressed(1, new TwistCommand());
        driveJoystick.whenPressed(11, new GyroReset(subsystemManager.getDriveSubsystem()));
        driveJoystick.whenPressed(9, new XCommand());
        subsystemManager.getDriveSubsystem()
                .setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), driveJoystick));
    }

    private OperatorInterface() {
    }
}