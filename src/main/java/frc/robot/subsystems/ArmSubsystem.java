package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.commands.MoveArmCommand;

public class ArmSubsystem extends EntechSubsystem {
    @Override
    public void initialize() {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    public  Command moveArm(){
        return new MoveArmCommand(this);
    }
}
