package frc.robot.OI;

public class UserPolicy {
  private static UserPolicy instance = new UserPolicy();

  private boolean twistable = false;

  private UserPolicy() {}

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
