package frc.robot;

import entech.util.EntechJoystick;

public class OperatorInterface {
    private CommandFactory commandFactory;
    private EntechJoystick joystick;
    private SubsystemInterface subs;

    public OperatorInterface(CommandFactory commandFactory, SubsystemInterface subs) {
        joystick = new EntechJoystick(RobotConstants.JoyStick.PORT);
        this.commandFactory = commandFactory;
        this.subs = subs;

    }

    public void setDefaults() {
        this.subs.getDriveSubsys().setDefaultCommand(this.commandFactory.driveCommand(joystick));
    }
}
