package frc.robot.OI;

import edu.wpi.first.math.geometry.Pose2d;

public class UserPolicy {
    private static UserPolicy instance = new UserPolicy();

    private boolean twistable = false;
    private Pose2d pose

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

}
