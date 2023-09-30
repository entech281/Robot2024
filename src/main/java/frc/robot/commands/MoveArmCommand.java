package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArmCommand extends EntechCommandBase {

    public MoveArmCommand(ArmSubsystem armSubsystem){
        super(armSubsystem);
    }
    private ArmSubsystem armSubsystem;
}
