package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class RobotConstants {
    public static interface Ports {
        public static class ANALOG {
            public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 1;
            // public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 2;
            public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 0;
            // public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 3;
        }

        public static class CAN {
            public static final int FRONT_LEFT_DRIVING = 10;
            // public static final int REAR_LEFT_DRIVING = 6;
            public static final int FRONT_RIGHT_DRIVING = 21;
            // public static final int REAR_RIGHT_DRIVING = 4;

            public static final int FRONT_LEFT_TURNING = 12;
            // public static final int REAR_LEFT_TURNING = 5;
            public static final int FRONT_RIGHT_TURNING = 22;
            // public static final int REAR_RIGHT_TURNING = 3;
        }

        public static class CONTROLLER {
            public static final int JOYSTICK = 0;
        }
    }

    public static interface Vision {
        public static interface Cameras {
            public static final String FRONT_LEFT = "0.0.0.0";
            public static final String FRONT_RIGHT = "0.0.0.0";
        }

        public static interface Filters {
            public static final double MAX_AMBIGUITY = 0.05;
            public static final double MIN_AREA = 0.75;
            public static final double MAX_DISTANCE = 2.5;
        }

        public static interface Offsets {
            public static final double FRONT_OFFSET_HEAVE_M = 0.05;
            public static final double FRONT_OFFSET_SWAY_M = 0.3;
            public static final double FRONT_OFFSET_SURGE_M = 0.3;
            public static final double FRONT_OFFSET_YAW_DEGREES = 45.0;
            public static final double FRONT_OFFSET_PITCH_DEGREES = 0.0;
            public static final double FRONT_OFFSET_ROLL_DEGREES = 0.0;
        }

        public static interface Transforms {
            public static final Transform3d FRONT_LEFT = new Transform3d(
                    new Translation3d(
                            Vision.Offsets.FRONT_OFFSET_SURGE_M,
                            Vision.Offsets.FRONT_OFFSET_SWAY_M,
                            Vision.Offsets.FRONT_OFFSET_HEAVE_M),
                    new Rotation3d(
                            Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_ROLL_DEGREES),
                            Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_PITCH_DEGREES),
                            Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_YAW_DEGREES)));
            public static final Transform3d FRONT_RIGHT = new Transform3d(
                    new Translation3d(
                            Vision.Offsets.FRONT_OFFSET_SURGE_M,
                            -Vision.Offsets.FRONT_OFFSET_SWAY_M,
                            Vision.Offsets.FRONT_OFFSET_HEAVE_M),
                    new Rotation3d(
                            -Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_ROLL_DEGREES),
                            Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_PITCH_DEGREES),
                            -Units.degreesToRadians(Vision.Offsets.FRONT_OFFSET_YAW_DEGREES)));
        }
    }

    /**
     * TODO: Fix this disaster
     */
    public static interface AUTONOMOUS {
        public static final double MAX_SPEED_METERS_PER_SECOND = 3.0; // 4.42; //3.0;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 3;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
        public static final double MAX_ANGULAR_ACCELERATION_RADIANS_PER_SECOND_SQUARED = Math.PI;

        public static final double X_CONTROLLER_P = 1;
        public static final double Y_CONTROLLER_P = 1;
        public static final double THETA_CONTROLLER_P = 1;

        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS = new TrapezoidProfile.Constraints(
                MAX_ANGULAR_SPEED_RADIANS_PER_SECOND, MAX_ANGULAR_ACCELERATION_RADIANS_PER_SECOND_SQUARED);
    }
}
