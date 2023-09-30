package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.subsystems.VisionSubsystem;

public class DriveForwardCommand extends EntechCommandBase {

    public DriveForwardCommand( VisionSubsystem ds ){
        super(ds);
    }
}
