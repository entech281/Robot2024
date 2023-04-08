package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.pose.Odometry;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private SubsystemInterface si;
  private CommandFactory cf;
  private OperatorInterface oi;
  private Odometry odometry;
  
  @Override
  public void robotInit() {
    si = new SubsystemInterface();
    odometry = new Odometry(si.getNavXSubSys().getYaw(), si.getDriveSubsys().getModulePositions());
    cf = new CommandFactory(si, odometry::getPose);
    oi = new OperatorInterface(cf);
  }

  @Override
  public void autonomousInit() {
    Command auto = cf.getAutoCommand();

    if (auto != null) {
      auto.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    genralPeriodic();
  }

  @Override
  public void teleopInit() {
    oi.setDefualtCommand(si.getDriveSubsys());
  }

  @Override
  public void disabledInit() {
    genralPeriodic();
  }

  @Override
  public void teleopPeriodic(){
  }

  @Override
  public void disabledPeriodic() {
  }

  public void genralPeriodic() {
    odometry.updateOdometry(si.getNavXSubSys().getYaw(), si.getDriveSubsys().getModulePositions());
  }
}