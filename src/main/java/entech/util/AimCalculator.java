package entech.util;

import java.lang.Math;
import edu.wpi.first.math.util.Units;

public class AimCalculator {
	// Constants
	private static final double pivotHeightInches = 18; // The height of the center of the pivot (in inches)
	private static final double armLenInches = 15; // The length from the center of the pivot to the point of intersection to the center of the shooter (in degrees)
	private static final double armAngleDegrees = 57; // The angle of the shooter (in degrees)

	/**
	 * 
	 * @param distanceToTargetMeters
	 * @return Angle the arm needs to be at in order to hit a specific target with
	 *         the note
	 */
	public static double getAngleDegreesFromDistance(double distanceToTargetMeters, double targetHeightInches) {
		double distanceToTargetInches = Units.metersToInches(distanceToTargetMeters);
		return Units.inchesToMeters(getAngleDegreesFromDistance(distanceToTargetInches, targetHeightInches,
				pivotHeightInches, armLenInches, armAngleDegrees));
	}

	private static final double getAngleDegreesFromDistance(double distanceToTarget, double heightOfTarget,
			double heightOfPivot, double lengthOfArm, double angleOfShooter) {

		double sideA = Math.sqrt(
				Math.pow(distanceToTarget, 2) + Math.pow((heightOfTarget - heightOfPivot), 2));

		double angleB = Math.toDegrees(
				Math.sin(lengthOfArm * Math.sin(Math.toRadians(angleOfShooter)) / sideA));

		double angleC = 180 - angleB - angleOfShooter;

		double sideD = distanceToTarget;

		double sideE = heightOfTarget - heightOfPivot;

		double angleE = Math
				.toDegrees(Math.acos((Math.pow(sideA, 2) + Math.pow(sideD, 2) - Math.pow(sideE, 2))
						/ (2 * sideA * sideD)));

		double angleTheta = 180 - (angleC + angleE);
		return angleTheta;
	}
}
