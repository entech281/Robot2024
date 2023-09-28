package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public final class RobotConstants {
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
}
