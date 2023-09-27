
package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import entech.commands.EntechCommandBase;
import entech.commands.suppliers.YawSupplier;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends EntechCommandBase {
    private DriveSubsystem drive;
    private YawSupplier navx;
    private Joystick js;

    public DriveCommand(DriveSubsystem drive, YawSupplier navx, Joystick js) {
        super(drive);

        this.navx = navx;
        this.js = js;
        this.drive = drive;
    }

    @Override
    public void execute() {
        drive.drive(new Translation2d(js.getX(), js.getY()), js.getZ(), true, Rotation2d.fromDegrees(navx.getYaw()));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interupted) {
        drive.stop();
    }
}