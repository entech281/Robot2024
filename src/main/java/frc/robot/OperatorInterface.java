package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class OperatorInterface {
    private CommandFactory commandFactory;
    private CommandJoystick joystick;

    public OperatorInterface(CommandFactory commandFactory) {
        joystick = new CommandJoystick(RobotConstants.JoyStick.PORT);
        this.commandFactory = commandFactory;
    }
}
