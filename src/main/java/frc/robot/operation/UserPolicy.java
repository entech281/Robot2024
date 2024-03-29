package frc.robot.operation;

import edu.wpi.first.math.geometry.Pose2d;

public class UserPolicy {
  private static UserPolicy instance = new UserPolicy();

  private boolean twistable = false;
  private boolean aligningToNote = false;
  private Pose2d targetPose = null;
  private boolean readyToShoot = false;

  private UserPolicy() {}

  public static UserPolicy getInstance() {
    return instance;
  }

  public boolean isReadyToShoot() {
    return this.readyToShoot;
  }

  public void setReadyToShoot(boolean readyToShoot) {
    this.readyToShoot = readyToShoot;
  }

  public boolean isTwistable() {
    return this.twistable;
  }

  public void setIsTwistable(boolean twistable) {
    this.twistable = twistable;
  }

  public void setTargetPose(Pose2d targetPose) {
    this.targetPose = targetPose;
  }

  public Pose2d getTargetPose() {
    return targetPose;
  }

  public boolean isAligningToNote() {
    return this.aligningToNote;
  }

  public void setAligningToNote(boolean aligningToNote) {
    this.aligningToNote = aligningToNote;
  }
}
