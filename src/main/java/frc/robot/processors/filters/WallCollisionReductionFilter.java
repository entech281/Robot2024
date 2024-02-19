package frc.robot.processors.filters;

import java.util.Optional;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.drive.DriveInput;

public class WallCollisionReductionFilter implements DriveFilterI {
@Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.latestOdometryPose = input.latestOdometryPose;

        Optional<Alliance> _team = DriverStation.getAlliance();
        Alliance team = _team.isPresent() ? _team.get() : Alliance.Blue;
        ChassisSpeeds robotSpeed = RobotIO.getInstance().getNavXOutput().chassisSpeeds;

        processedInput.xSpeed = calculateXPercent(input.xSpeed, input.latestOdometryPose.getX(), robotSpeed.vxMetersPerSecond, team);
        processedInput.ySpeed = calculateXPercent(input.ySpeed, input.latestOdometryPose.getY(), robotSpeed.vyMetersPerSecond, team);
        processedInput.rot = input.rot;

        return processedInput;
    }

    public static interface WallCollisionReduction {
        public static final double SAFETY_DISTANCE = 1.5;
        public static final double COORDINATE_DELAY_TIME = 20 / 1000;
        public static final double X_FAR_WALL_DISTANCE = 16.54175;
        public static final double Y_FAR_WALL_DISTANCE = 8.2;
        public static final double IN_SAFETY_LIMIT_PERCENT = 0.1;
    }

    public static double calculateXPercent(double xPercent, double xCord, double currentXSpeed, Alliance team) {
        return doDirectionalLimit(xCord, WallCollisionReduction.X_FAR_WALL_DISTANCE, currentXSpeed, xPercent, team);
    }

    public static double calculateYPercent(double yPercent, double yCord, double currentYSpeed, Alliance team) {
        return doDirectionalLimit(yCord, WallCollisionReduction.Y_FAR_WALL_DISTANCE, currentYSpeed, yPercent, team);
    }

    public static double doDirectionalLimit(double cord, double maxCord, double speed, double percent, Alliance team) {
        double nextSecondCord = calculateNextSecondCord(maxCord, speed);

        double minFieldPercent = 1.0;
        double maxFieldPercent = 1.0;

        double maxSafety = maxCord - WallCollisionReduction.SAFETY_DISTANCE;

        if (nextSecondCord <= 0) {
            minFieldPercent = 0.0;
        } else if (nextSecondCord < WallCollisionReduction.SAFETY_DISTANCE) {
            minFieldPercent = Math.pow(nextSecondCord / WallCollisionReduction.SAFETY_DISTANCE, Math.abs(speed));
        }

        if (nextSecondCord > maxSafety) {
            maxFieldPercent = 0.0;
        } if (nextSecondCord > maxCord) {
            maxFieldPercent = Math.pow((nextSecondCord - maxSafety) / WallCollisionReduction.SAFETY_DISTANCE, Math.abs(speed));
        }

        if (team == Alliance.Blue) {
            return MathUtil.clamp(percent, -minFieldPercent, maxFieldPercent);
        }
        return MathUtil.clamp(percent, -maxFieldPercent, minFieldPercent);
    }

    public static double calculateCorrectCord(double cord, double speed) {
        return cord + (speed * WallCollisionReduction.COORDINATE_DELAY_TIME);
    }

    public static double calculateNextSecondCord(double cord, double speed) {
        return calculateCorrectCord(cord, speed) + speed;
    }
}
