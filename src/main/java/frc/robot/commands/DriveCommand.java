
package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Joystick;
import entech.commands.EntechCommandBase;
import entech.commands.suppliers.YawSupplier;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommandBase {
    private DriveSubsystem drive;
    private YawSupplier navx;

    private Joystick js;

    private BooleanSupplier robotCentricSup;

    public DriveCommand(DriveSubsystem drive, YawSupplier navx, Joystick js, BooleanSupplier robotCentricSup) {
        super(drive);

        this.drive = drive;
        this.navx = navx;

        this.js = js;

        this.robotCentricSup = robotCentricSup;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interupted) {

    }
}