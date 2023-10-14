package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.OI.UserPolicy;

public class TwistCommand extends EntechCommandBase {
    public TwistCommand() {

    }

    @Override
    public void initialize() {
        UserPolicy.twistable = true;
    }

    @Override
    public void end(boolean interrupted) {
        UserPolicy.twistable = false;
    }
}
