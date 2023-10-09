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
            public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 2;
            public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 0;
            public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 3;
        }

        public static class CAN {
            public static final int FRONT_LEFT_DRIVING = 10;
            public static final int REAR_LEFT_DRIVING = 6;
            public static final int FRONT_RIGHT_DRIVING = 21;
            public static final int REAR_RIGHT_DRIVING = 4;

            public static final int FRONT_LEFT_TURNING = 12;
            public static final int REAR_LEFT_TURNING = 5;
            public static final int FRONT_RIGHT_TURNING = 22;
            public static final int REAR_RIGHT_TURNING = 3;
        }

        public static class CONTROLLER {
            public static final int JOYSTICK = 0;
        }
    }

    public static interface Vision {
        public static interface Cameras {
            public static final String FRONT_LEFT = "";
            public static final String FRONT_RIGHT = "USB_Camera";
        }

        public static interface Filters {
            public static final double MAX_AMBIGUITY = 0.05;
            public static final double MIN_AREA = 0.75;
            public static final double MAX_DISTANCE = 2.5;
        }

        public static interface Offsets {
            public static final double FRONT_OFFSET_HEAVE_M = 0.0;
            public static final double FRONT_OFFSET_SWAY_M = 0.25;
            public static final double FRONT_OFFSET_SURGE_M = 0.0;
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
    public interface ARM{
    	public interface SETTINGS{
    		public static boolean MOTOR_REVERSED = true;
    		public static final double MOVE_TOLERANCE_METERS = 0.012; 
    		public static final int CURRENT_LIMIT_AMPS = 8;
    		public static final int MAX_SPIKE_CURRENT = 12;
    		public static final double COUNTS_PER_METER = 0.004826; 
    		
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT = 0.35;
    		public static final double HOMING_SPEED_VELOCITY = 5.0; //we think this is 'arm meters per minute'
    		public static final int HOMING_CURRENT_AMPS=7;
    	}
    	public interface TUNING{
    		public static final double P_GAIN = 16.0;
    		public static final double I_GAIN = 0.0;
    		public static final double D_GAIN = 0.0;
    	}
    	public interface POSITION_PRESETS{
    		  public static final double MIN_METERS = 0.005;
    		  public static final double CARRY_METERS = 0.1;
    		  public static final double SCORE_MIDDLE_METERS = 0.17;
    		  public static final double SCORE_HIGH_METERS = 0.49;
    		  public static final double SAFE = 0.1;
              public static final double LOAD = 0.005;
    		  public static final double MAX_METERS = 0.50; //0.52 max extension
              //public static final double MIN_ARM_LENGTH_M = 0.87155; 
              //public static final double MAX_ARM_LENGTH_M = 1.45;
    	}
        
        public static final double MIN_EXTENSION_METERS = 0.9906; //39 inches from center of robot to center of claw
        public static final double MAX_EXTENSION_METERS = 1.4478; //57 inches from center of robot to center of claw
        //public static final double ARM_MAX_REACH_METERS = 0.8128; //from center of robot base!!!
    }
    public interface ELBOW{
        public static final double MIN_ANGLE_DEGREES = 12.0; //78 degrees below horizontal (90)
        public static final double MAX_ANGLE_DEGREES = 107.0; //17 degrees above horizontal (90)
    	public interface SETTINGS{
    		public static boolean MOTOR_REVERSED = false;

    		public static final double MOVE_TOLERANCE_DEGREES= 1.0; 
    		public static final int CURRENT_LIMIT_AMPS=30;
    		public static final int MAX_SPIKE_CURRENT=50;
    		public static final double COUNTS_PER_DEGREE=1.95; 
    		public static final double ELBOW_SLOWDOWN_SPEED= 0.2;
    		public static final double MIDDLE_HIGH_CONE_DEPLOY_THRESHOLD = 83.0;
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT = 0.2;
    		public static final double HOMING_SPEED_VELOCITY = 5.0; //we think this is 'elbow degrees per minute'
    		public static final int HOMING_CURRENT_AMPS=7;    		
    	}
    	public interface TUNING{

    		public static final double P_GAIN=0.08;
    		public static final double FF_GAIN_GOING_UP=0.00;
    		public static final double FF_GAIN_GOING_DOWN=0.0;
    		public static final double I_GAIN=0.000;
    		public static final double D_GAIN=0.0;

    	}
    	public interface POSITION_PRESETS{
    		  public static final double MIN_POSITION_DEGREES = 18.0;    		
    		  public static double CARRY_DEGREES = 20.0;
    		  public static double SAFE_ANGLE = 35.0;  
    		  public static double SCORE_LOW_DEGREES = 43.0;
    		  public static double SCORE_MIDDLE_DEGREES = 79.0;
    		  public static double LOAD_STATION_DEGREES = 75.0;
    		  public static double SCORE_HIGH_DEGREES = 94.0;
    		  public static double SCORE_HIGH_RELEASE_DEGREES = 83.0;
    		  public static double SCORE_MID_RELEASE_DEGREES = 71;
     		  public static final double MAX_POSITION_DEGREES = 104.8; 
    	}
    } 
    public interface INDICATOR_VALUES{
    	public static final double POSITION_UNKNOWN = -1.0;
    	public static final double POSITION_NOT_SET = -1.1;
    }   

    public interface CAN {
    	public static final int FRONT_LEFT_MOTOR = 1;
    	public static final int FRONT_RIGHT_MOTOR = 2;
    	public static final int REAR_LEFT_MOTOR = 3;
    	public static final int REAR_RIGHT_MOTOR = 4;    	
        public static final int ELBOW_MOTOR_ID = 5;
        public static final int TELESCOPE_MOTOR_ID = 6;
    }
}
