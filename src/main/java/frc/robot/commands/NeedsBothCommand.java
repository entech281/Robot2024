package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class NeedsBothCommand extends EntechCommandBase {

    public NeedsBothCommand(VisionSubsystem vision, ArmSubsystem arm){
        super(vision, arm);
    }
}
