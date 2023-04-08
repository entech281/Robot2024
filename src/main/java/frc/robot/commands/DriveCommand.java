
package frc.robot.commands;

import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;

import entech.commands.suppliers.YawSupplier;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;


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
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(-js.getY(), RobotConstants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(js.getX(), RobotConstants.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(js.getZ(), RobotConstants.stickDeadband);

        /* Drive */
        drive.drive(
            new Translation2d(translationVal, strafeVal).times(RobotConstants.Swerve.MAX_SPEED), 
            rotationVal * RobotConstants.Swerve.MAX_ANGULAR_VELOCITY, 
            !robotCentricSup.getAsBoolean(), 
            true,
            navx.getYaw()
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interupted) {

    }
}