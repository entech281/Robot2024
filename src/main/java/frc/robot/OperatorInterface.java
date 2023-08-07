package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class OperatorInterface {
    private CommandFactory commandFactory;
    private CommandJoystick joystick;
    private SubsystemInterface subs;

    public OperatorInterface(CommandFactory commandFactory, SubsystemInterface subs) {
        joystick = new CommandJoystick(RobotConstants.JoyStick.PORT);
        this.commandFactory = commandFactory;
        this.subs = subs;

        this.subs.getDriveSubsys().setDefaultCommand(this.commandFactory.driveCommand(joystick));
    }
}
