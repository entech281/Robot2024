package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

public final class RobotConstants {
  public static final class DrivetrainConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double MAX_SPEED_METERS_PER_SECOND = 4.0; // 4.42; //4.8;
    public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = 2 * Math.PI;
    // radians per second

    public static final double DIRECTION_SLEW_RATE = 1.2; // radians per second
    public static final double MAGNITUDE_SLEW_RATE = 1.8;
    // 2.0; //1.8; // percent per second (1 = 100%)
    public static final double ROTATIONAL_SLEW_RATE = 2.0;
    // 20.0; //2.0; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(21.5);

    // Distance between centers of right and left wheels on robot
    public static final double WHEEL_BASE_METERS = Units.inchesToMeters(18);

    // Distance to farthest module
    public static final double DRIVE_BASE_RADIUS_METERS = 0.39;

    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics DRIVE_KINEMATICS =
        new SwerveDriveKinematics(new Translation2d(WHEEL_BASE_METERS / 2, TRACK_WIDTH_METERS / 2),
            new Translation2d(WHEEL_BASE_METERS / 2, -TRACK_WIDTH_METERS / 2),
            new Translation2d(-WHEEL_BASE_METERS / 2, TRACK_WIDTH_METERS / 2),
            new Translation2d(-WHEEL_BASE_METERS / 2, -TRACK_WIDTH_METERS / 2));

    public static final boolean GYRO_REVERSED = false;
    public static final boolean RATE_LIMITING = true;
  }


  public static final class SwerveModuleConstants {
    public static final double FREE_SPEED_RPM = 5676;

    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth
    // will result in a
    // robot that drives faster).
    public static final int DRIVING_MOTOR_PINION_TEETH = 14;

    // Invert the turning encoder, since the output shaft rotates in the opposite
    // direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean TURNING_ENCODER_INVERTED = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double DRIVING_MOTOR_FREE_SPEED_RPS = FREE_SPEED_RPM / 60;
    public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4.125); // 0.0972;
    public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;
    public static final double DRIVING_MOTOR_REDUCTION =
        (45.0 * 17 * 50) / (DRIVING_MOTOR_PINION_TEETH * 15 * 27);
    public static final double DRIVE_WHEEL_FREE_SPEED_RPS =
        (DRIVING_MOTOR_FREE_SPEED_RPS * WHEEL_CIRCUMFERENCE_METERS) / DRIVING_MOTOR_REDUCTION;

    public static final double DRIVING_ENCODER_POSITION_FACTOR_METERS_PER_ROTATION =
        (WHEEL_DIAMETER_METERS * Math.PI) / DRIVING_MOTOR_REDUCTION; // meters, per rotation
    public static final double DRIVING_ENCODER_VELOCITY_FACTOR_METERS_PER_SECOND_PER_RPM =
        ((WHEEL_DIAMETER_METERS * Math.PI) / DRIVING_MOTOR_REDUCTION) / 60.0;
    // meters per second, per RPM

    public static final double TURNING_MOTOR_REDUCTION = 150.0 / 7.0;
    // ratio between internal relative encoder and
    // Through Bore (or Thrifty in our case)
    // absolute encoder - 150.0 / 7.0

    public static final double TURNING_ENCODER_POSITION_FACTOR_RADIANS_PER_ROTATION =
        (2 * Math.PI) / TURNING_MOTOR_REDUCTION; // radians, per rotation
    public static final double TURNING_ENCODER_VELOCITY_FACTOR_RADIANS_PER_SECOND_PER_RPM =
        (2 * Math.PI) / TURNING_MOTOR_REDUCTION / 60.0; // radians per second, per RPM

    public static final double TURNING_ENCODER_POSITION_PID_MIN_INPUT_RADIANS = 0; // radians
    public static final double TURNING_ENCODER_POSITION_PID_MAX_INPUT_RADIANS = (2 * Math.PI);
    // radians

    public static final double DRIVING_P = 0.04;
    public static final double DRIVING_I = 0;
    public static final double DRIVING_D = 0;
    public static final double DRIVING_FF = 1 / DRIVE_WHEEL_FREE_SPEED_RPS;
    public static final double DRIVING_MIN_OUTPUT_NORMALIZED = -1;
    public static final double DRIVING_MAX_OUTPUT_NORMALIZED = 1;

    public static final double TURNING_P = 1.0;
    // 1.0; // 1.0 might be a bit too much - reduce a bit if needed
    public static final double TURNING_I = 0;
    public static final double TURNING_D = 0;
    public static final double TURNING_FF = 0;
    public static final double TURNING_MIN_OUTPUT_NORMALIZED = -1;
    public static final double TURNING_MAX_OUTPUT_NORMALIZED = 1;

    public static final IdleMode DRIVING_MOTOR_IDLE_MODE = IdleMode.kBrake;
    public static final IdleMode TURNING_MOTOR_IDLE_MODE = IdleMode.kBrake;

    public static final int DRIVING_MOTOR_CURRENT_LIMIT_AMPS = 40; // 50; // amps
    public static final int TURNING_MOTOR_CURRENT_LIMIT_AMPS = 20; // amps
  }


  public static interface LED {
    public static final int PORT = 0;
    public static final int NUM_LEDS = 42;
    public static final double BLINK_INTERVAL = 0.25;
  }


  public static interface TEST_CONSTANTS {

    public static final int STANDARD_TEST_LENGTH = 1;

    public static interface PIVOT {
      public static final double TEST_TOLERANCE_DEG = 1;
      public static final double TEST_POSITION_DEG = 27;
    }

    public static interface SHOOTER {
      public static final double TESTING_SPEED = 5000;
    }

    public static interface CLIMB {
      public static final double TEST_TOLERANCE_IN = 1;
      public static final double TEST_POSITION_IN = 50;
    }
  }


  public static interface PORTS {

    public static class ANALOG {
      public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 3;
      public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 0;
      public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 2;
      public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 1;
    }


    public static class CAN {
      public static final int FRONT_LEFT_DRIVING = 12;
      public static final int FRONT_RIGHT_DRIVING = 22;
      public static final int REAR_LEFT_DRIVING = 32;
      public static final int REAR_RIGHT_DRIVING = 42;

      public static final int FRONT_LEFT_TURNING = 11;
      public static final int FRONT_RIGHT_TURNING = 21;
      public static final int REAR_LEFT_TURNING = 31;
      public static final int REAR_RIGHT_TURNING = 41;

      public static final int SHOOTER_B = 9;
      public static final int SHOOTER_A = 8;

      public static final int TRANSFER = 7;

      public static final int INTAKE = 4;

      public static final int PIVOT_B = 5;
      public static final int PIVOT_A = 6;

      public static final int CLIMB_B = 3;
      public static final int CLIMB_A = 2;
    }


    public static class CONTROLLER {
      public static final double JOYSTICK_AXIS_THRESHOLD = 0.2;
      public static final int DRIVER_CONTROLLER = 0;
      public static final int PANEL = 1;
      public static final int TEST_JOYSTICK = 2;

      public static class BUTTONS_JOYSTICK {
        public static final int TWIST = 1;
        public static final int RUN_TESTS = 7;
        public static final int GYRO_RESET = 11;
        public static final int RESET_ODOMETRY = 3;
        public static final int CLIMB_JOG = 9;
      }

      public static class BUTTONS_XBOX {
        public static final int GYRO_RESET = 7;
        public static final int INTAKE = 2;
        public static final int FULL_PIVOT = 8;
      }
    }


    public static class HAS_NOTE {
      public static final int INTERNAL_SENSOR_FORWARD = 0;
      public static final int INTERNAL_SENSOR_REAR = 1;
    }
  }


  public interface OPERATOR_PANEL {
    public static class BUTTONS {
      public static final int SHOOT = 1;
      public static final int INTAKE = 4;
      public static final int EJECT = 5;
      public static final int CLIMB = 7;
    }

    public static class SWITCHES {
      public static final int PIVOT_AMP = 2;
      public static final int PIVOT_SPEAKER = 3;
      public static final int CANCEL_CLIMB = 6;
    }
  }

  public static interface Vision {
    public static final Matrix<N3, N1> VISION_STD_DEVS = VecBuilder.fill(10, 10, 1000000);


    public static interface Cameras {
      public static final String LEFT = "Aducam_Bravo";
      public static final String RIGHT = "Arducam_Alpha";
      public static final String COLOR = "Arducam_OV9782_USB_Camera";
      public static final String MIDDLE = "Global_Shutter_Camera";
    }


    public static interface Filters {
      public static final double MAX_AMBIGUITY = 0.4;
      public static final double MAX_DISTANCE = 3;
    }

    public static interface Resolution {
      public static final double[] COLOR_RESOLUTION = {320, 240};
    }


    public static interface Transforms {
      public static final Transform3d LEFT = new Transform3d(
          new Translation3d(Units.inchesToMeters(17.875), Units.inchesToMeters(4.25),
              Units.inchesToMeters(19.5)),
          new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(10),
              Units.degreesToRadians(-90)));
      public static final Transform3d RIGHT = new Transform3d(
          new Translation3d(Units.inchesToMeters(17.875), Units.inchesToMeters(-4.25),
              Units.inchesToMeters(19.5)),
          new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(10),
              Units.degreesToRadians(90)));
      public static final Transform3d MIDDLE = new Transform3d(
          new Translation3d(Units.inchesToMeters(17.875), Units.inchesToMeters(1.25),
              Units.inchesToMeters(19.5)),
          new Rotation3d(Units.degreesToRadians(0), Units.degreesToRadians(15),
              Units.degreesToRadians(0.0)));
    }
  }


  public static interface PID {

    public static interface SHOOTER {
      public static final double KP = 0.000415;
      public static final double KI = 0.0;
      public static final double KD = 0.0;
      public static final double KFF = 0.00018;
      public static final double MAX_SPEED = 4000;
    }


    public static interface PIVOT {
      public static final double KP = 0.00004;
      public static final double KI = 0;
      public static final double KD = 0;
    }


    public static interface CLIMB {
      public static final double KP = 0.00015;
      public static final double KI = 0;
      public static final double KD = 0;
    }
  }


  public static interface TRANSFER {
    public static final double SHOOTING_SPEED = 1;
    public static final double TRANSFERRING_SPEED = 0.0;
    public static final double INTAKING_SPEED_FAST = 0.7;
    public static final double INTAKING_SPEED_SLOW = 0;
    public static final double EJECTING_SPEED = -1;
    public static final double RETRACTING_SPEED = -0.2;
    public static final double TESTING_SPEED = 0.15;
  }


  public static interface PIVOT {
    public static final double UPPER_SOFT_LIMIT_DEG = 85.5;
    public static final double LOWER_SOFT_LIMIT_DEG = 1;
    public static final double PIVOT_CONVERSION_FACTOR = 2.4;
    public static final double SHOOT_AMP_POSITION_DEG = 85.5;
    public static final double POSITION_TOLERANCE_DEG = 2;
    public static final double INITIAL_POSITION = 1;

    public static final double kA = 2.31586;
    public static final double kB = -25.1345;
    public static final double kC = 94.4448;
    public static final double kD = -132.894;
    public static final double kE = 76.0679;

    public static final double LOB_ANGLE = 10.0;

    public static final double SPEAKER_BUMPER_SCORING = 15.2;
    public static final double SPEAKER_PODIUM_SCORING = 25;
  }


  public static interface CLIMB {
    public static final double UPPER_SOFT_LIMIT_Inches = 25.0;
    public static final double CLIMB_CONVERSION_FACTOR = 1.0;
    public static final double CLIMB_RETRACTED = 0;
    public static final double CLIMB_EXTENDED = 250;
  }


  public static interface INTAKE {
    public static final double INTAKE_SPEED = 0.8;
    public static final int EJECTING_TIME = 2;
  }


  public static interface SHOOTER {
    public static final double RESET_DELAY = 0.5;
    public static final double SHOOT_DELAY = 0.25;
  }


  public static interface AUTONOMOUS {
    public static final double MAX_MODULE_SPEED_METERS_PER_SECOND = 4.42; // 4.42

    public static final double TRANSLATION_CONTROLLER_P = 5;
    public static final double ROTATION_CONTROLLER_P = 5;
  }


  public interface INDICATOR_VALUES {
    public static final double POSITION_UNKNOWN = -1.0;
    public static final double POSITION_NOT_SET = -1.1;
  }


  public interface ODOMETRY {
    public static final double FIELD_LENGTH_INCHES = 54 * 12 + 3.25;
    public static final double FIELD_WIDTH_INCHES = 26 * 12 + 11.25;

    public static final Translation2d INITIAL_TRANSLATION =
        new Translation2d(Units.inchesToMeters(FIELD_LENGTH_INCHES / 2),
            Units.inchesToMeters(FIELD_WIDTH_INCHES / 2)); // mid
                                                           // field
    public static final Rotation2d INITIAL_ROTATION = Rotation2d.fromDegrees(0);

    public static final Pose2d INITIAL_POSE = new Pose2d(INITIAL_TRANSLATION, INITIAL_ROTATION);
  }


  public interface OperatorMessages {
    public static final String SUBSYSTEM_TEST = "SubsystemTest";
  }

  private RobotConstants() {}
}
