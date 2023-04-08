package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.COTSFalconSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class RobotConstants {
    public static final double stickDeadband = 0.1;

    public static interface JoyStick {
        public static final int PORT = 0;
    }

    public static interface Swerve {
        public static final COTSFalconSwerveConstants CHOSEN_MODULE =  //TODO: This must be tuned to specific robot
            COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L2);

        /* Drivetrain Constants */
        public static final double TRACK_WIDTH = Units.inchesToMeters(21.73); //TODO: This must be tuned to specific robot
        public static final double WHEEL_BASE = Units.inchesToMeters(21.73); //TODO: This must be tuned to specific robot
        public static final double WHEEL_CIRCUMFERENCE = CHOSEN_MODULE.wheelCircumference;

        /* Swerve Kinematics 
         * No need to ever change this unless you are not doing a traditional rectangular/square 4 module swerve */
         public static final SwerveDriveKinematics SWERVE_KINEMATICS = new SwerveDriveKinematics(
            new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
            new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0),
            new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
            new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0));

        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = CHOSEN_MODULE.driveGearRatio;
        public static final double ANGLE_GEAR_RATIO = CHOSEN_MODULE.angleGearRatio;

        /* Motor Inverts */
        public static final boolean ANGLE_MOTOR_INVERT = CHOSEN_MODULE.angleMotorInvert;
        public static final boolean DRIVE_MOTOR_INVERT = CHOSEN_MODULE.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean CAN_CODER_INVERT = CHOSEN_MODULE.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int ANGLE_CONTINUOUS_CURRENT_LIMIT = 25;
        public static final int ANGLE_PEAK_CURRENT_LIMIT = 40;
        public static final double ANGLE_PEAK_CURRENT_DURATION = 0.1;
        public static final boolean ANGLE_ENABLE_CURRENT_LIMIT = true;

        public static final int DRIVE_CONTINUOUS_CURRENT_LIMIT = 35;
        public static final int DRIVE_PEAK_CURRENT_LIMIT = 60;
        public static final double DRIVE_PEAK_CURRENT_DURATION = 0.1;
        public static final boolean DRIVE_ENABLE_CURRENT_LIMIT = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;

        /* Angle Motor PID Values */
        public static final double ANGLE_KP = CHOSEN_MODULE.angleKP;
        public static final double ANGLE_KI = CHOSEN_MODULE.angleKI;
        public static final double ANGLE_KD = CHOSEN_MODULE.angleKD;
        public static final double ANGLE_KF = CHOSEN_MODULE.angleKF;

        /* Drive Motor PID Values */
        public static final double DRIVE_KP = 0.05; //TODO: This must be tuned to specific robot
        public static final double DRIVE_KI = 0.0;
        public static final double DRIVE_KD = 0.0;
        public static final double DRIVE_KF = 0.0;

        /* Drive Motor Characterization Values 
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        public static final double DRIVE_KS = (0.32 / 12); //TODO: This must be tuned to specific robot
        public static final double DRIVE_KV = (1.51 / 12);
        public static final double DRIVE_KA = (0.27 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double MAX_SPEED = 4.5; //TODO: This must be tuned to specific robot
        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = 10.0; //TODO: This must be tuned to specific robot

        /* Neutral Modes */
        public static final NeutralMode ANGLE_NEUTRAL_MODE = NeutralMode.Coast;
        public static final NeutralMode DRIVE_NEUTRAL_MODE = NeutralMode.Brake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 { //TODO: This must be tuned to specific robot
            public static final int DRIVE_MOTOR_ID = 10;
            public static final int ANGLE_MOTOR_ID = 11;
            public static final int CAN_CODER_ID = 12;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0.0);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 { //TODO: This must be tuned to specific robot
            public static final int DRIVE_MOTOR_ID = 20;
            public static final int ANGLE_MOTOR_ID = 21;
            public static final int CAN_CODER_ID = 22;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0.0);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }
        
        /* Back Left Module - Module 2 */
        public static final class Mod2 { //TODO: This must be tuned to specific robot
            public static final int DRIVE_MOTOR_ID = 30;
            public static final int ANGLE_MOTOR_ID = 31;
            public static final int CAN_CODER_ID = 32;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0.0);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 { //TODO: This must be tuned to specific robot
            public static final int DRIVE_MOTOR_ID = 40;
            public static final int ANGLE_MOTOR_ID = 41;
            public static final int CAN_CODER_ID = 42;
            public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0.0);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
        }
    }

    public static final class AutoConstants { //TODO: The below constants are used in the example auto, and must be tuned to specific robot
        public static final double MAX_SPEED_METERS_PER_SECOND = 3;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 3;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND_SQUARED = Math.PI;
    
        public static final double PX_CONTROLLER = 1;
        public static final double PY_CONTROLLER = 1;
        public static final double P_THETA_CONTROLLER = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS =
            new TrapezoidProfile.Constraints(
                MAX_ANGULAR_SPEED_RADIANS_PER_SECOND, MAX_ANGULAR_SPEED_RADIANS_PER_SECOND_SQUARED);
    }
}
