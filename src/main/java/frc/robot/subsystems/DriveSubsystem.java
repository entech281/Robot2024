package frc.robot.subsystems;

import frc.lib.SwerveModule;
import frc.robot.RobotConstants;

import entech.subsystems.EntechSubsystem;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.util.sendable.SendableBuilder;

public class DriveSubsystem extends EntechSubsystem {
    private SwerveModule[] mSwerveMods;

    @Override
    public void initialize() {
        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, RobotConstants.Swerve.Mod0.constants),
            new SwerveModule(1, RobotConstants.Swerve.Mod1.constants),
            new SwerveModule(2, RobotConstants.Swerve.Mod2.constants),
            new SwerveModule(3, RobotConstants.Swerve.Mod3.constants)
        };

        /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
         * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
         */
        Timer.delay(1.0);
        resetModulesToAbsolute();
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop, double yaw) {
        SwerveModuleState[] swerveModuleStates =
            RobotConstants.Swerve.SWERVE_KINEMATICS.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    Rotation2d.fromDegrees(yaw)
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, RobotConstants.Swerve.MAX_SPEED);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, RobotConstants.Swerve.MAX_SPEED);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }

    @Override
    public void periodic() {
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(getName());
        for(SwerveModule mod : mSwerveMods){
            builder.addDoubleProperty("Mod " + mod.moduleNumber + " Cancoder", () -> { return mod.getCanCoder().getDegrees();}, null);
            builder.addDoubleProperty("Mod " + mod.moduleNumber + " Integrated", () -> { return mod.getPosition().angle.getDegrees();}, null);
            builder.addDoubleProperty("Mod " + mod.moduleNumber + " Velocity", () -> { return mod.getState().speedMetersPerSecond;}, null);
        }
    } 

    @Override
    public boolean isEnabled() {
        return true;
    }
}