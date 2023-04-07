package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.DriveCommand;

public class CommandFactory {
    public SubsystemInterface subsystems;
    public CommandFactory(SubsystemInterface subsystems) {
        this.subsystems = subsystems;
    }

    public Command driveCommand(Joystick js) {
        return new DriveCommand(subsystems.getDriveSubsys(), subsystems.getNavXSubSys(), js, () -> { return false;});
    }
}
