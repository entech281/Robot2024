package frc.robot.OI;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class UserPolicy {
    private static UserPolicy instance = new UserPolicy();

    private boolean twistable = false;
    private Pose2d targetPose = new Pose2d(0, 5.53, new Rotation2d());

    private UserPolicy() {
    }

    public static UserPolicy getInstance() {
        return instance;
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
}
