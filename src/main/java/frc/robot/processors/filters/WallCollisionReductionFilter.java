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
    DriveInput processedInput = new DriveInput(input);

    Optional<Alliance> optTeam = DriverStation.getAlliance();
    Alliance team = optTeam.isPresent() ? optTeam.get() : Alliance.Blue;
    ChassisSpeeds robotSpeed = RobotIO.getInstance().getNavXOutput().getChassisSpeeds();

    processedInput.setXSpeed(calculateXPercent(input.getXSpeed(), input.getLatestOdometryPose().getX(), robotSpeed.vxMetersPerSecond, team));
    processedInput.setYSpeed(calculateXPercent(input.getYSpeed(), input.getLatestOdometryPose().getY(), robotSpeed.vyMetersPerSecond, team));

    return processedInput;
  }

  public static interface WallCollisionReduction {
    public static final double SAFETY_DISTANCE = 1.5;
    public static final double COORDINATE_DELAY_TIME = 20.0 / 1000.0;
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
    double nextSecondCord = calculateNextSecondCord(cord, speed);

    double minFieldPercent = 1.0;
    double maxFieldPercent = 1.0;

    double maxSafety = maxCord - WallCollisionReduction.SAFETY_DISTANCE;

    if (nextSecondCord <= 0.0) {
      minFieldPercent = 0.0;
    } else if (nextSecondCord < WallCollisionReduction.SAFETY_DISTANCE) {
      minFieldPercent = calculateLimit(nextSecondCord, speed);
    }

    if (nextSecondCord >= maxCord) {
      maxFieldPercent = 0.0;
    } else if (nextSecondCord > maxSafety) {
      maxFieldPercent = calculateLimit(WallCollisionReduction.SAFETY_DISTANCE - (nextSecondCord - maxSafety), speed);
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

  public static double calculateLimit(double nextSecondDistanceFromWall, double speed) {
    return Math.pow(nextSecondDistanceFromWall / WallCollisionReduction.SAFETY_DISTANCE, Math.abs(speed));
  }
}
