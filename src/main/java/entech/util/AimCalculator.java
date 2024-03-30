package entech.util;

import edu.wpi.first.math.util.Units;
import frc.robot.RobotConstants;

public class AimCalculator {

	public enum Target {
		SPEAKER(85), TRAP(0);

		private int numVal;

		Target(int numVal) {
			this.numVal = numVal;
		}

		public int getNumVal() {
			return numVal;
		}
	}

	/**
	 *
	 * @param distanceToTargetMeters
	 * @return Angle the arm needs to be at in order to hit a specific target with the note
	 */
	public static double getAngleDegreesFromDistance(double distanceToTargetMeters, Target target) {
		double distanceToTargetInches = Units.metersToInches(distanceToTargetMeters) - 0.0;

		double targetHeight = target.getNumVal();

		return getAngleDegreesFromDistance(distanceToTargetInches, targetHeight,
				RobotConstants.AUTO_AIM.PIVOT_HEIGHT_INCH, RobotConstants.AUTO_AIM.ARM_LENGTH_INCHES,
				RobotConstants.AUTO_AIM.ARM_ANGLE_DEGREES);
	}

	private static final double getAngleDegreesFromDistance(double distanceToTarget,
			double heightOfTarget, double heightOfPivot, double lengthOfArm, double angleOfShooter) {

		double sideA =
				Math.sqrt(Math.pow(distanceToTarget, 2) + Math.pow((heightOfTarget - heightOfPivot), 2));

		double angleB =
				Math.toDegrees(Math.sin(lengthOfArm * Math.sin(Math.toRadians(angleOfShooter)) / sideA));

		double angleC = 180 - angleB - angleOfShooter;

		double sideD = distanceToTarget;

		double sideE = heightOfTarget - heightOfPivot;

		double angleE = Math.toDegrees(Math.acos(
				(Math.pow(sideA, 2) + Math.pow(sideD, 2) - Math.pow(sideE, 2)) / (2 * sideA * sideD)));

		double angleTheta = 180 - (angleC + angleE);
		return angleTheta;
	}

	public static double getPivotAngleFromDistance(double currentDistance) {
		double pivotAngle = (-0.0016 * Math.pow((Units.metersToInches(currentDistance)
				- RobotConstants.AUTO_AIM.DISTANCE_FROM_REAR_TO_CENTER), 2))
				+ (0.5394 * (Units.metersToInches(currentDistance)
						- RobotConstants.AUTO_AIM.DISTANCE_FROM_REAR_TO_CENTER))
				- 6.5;
		return pivotAngle;
	}
}
