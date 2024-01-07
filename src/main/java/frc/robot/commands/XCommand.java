package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.OI.UserPolicy;

public class XCommand extends EntechCommand {
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
