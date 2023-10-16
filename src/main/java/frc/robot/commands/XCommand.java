package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.OI.UserPolicy;

public class XCommand extends EntechCommandBase {
    public XCommand() {

    }

    @Override
    public void initialize() {
        UserPolicy.xLocked = !UserPolicy.xLocked;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
