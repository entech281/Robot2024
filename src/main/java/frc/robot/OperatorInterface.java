package frc.robot;

import entech.subsystems.EntechSubsystem;

import frc.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class OperatorInterface {
    private CommandFactory commandFactory;
    private CommandJoystick joystick;

    public OperatorInterface(CommandFactory commandFactory) {
        joystick = new CommandJoystick(RobotConstants.JoyStick.PORT);
        this.commandFactory = commandFactory;
    }

    public void setDefualtCommand(EntechSubsystem subsys) {
        if (subsys instanceof DriveSubsystem && subsys.isEnabled()) {
            subsys.setDefaultCommand(commandFactory.driveCommand(joystick.getHID()));
        }
    }
}
