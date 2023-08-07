package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
  
  @Override
  public void robotInit() {
    si = new SubsystemInterface();

    si.initialize();

    cf = new CommandFactory(si, null);
    
    oi = new OperatorInterface(cf, si);
  }


  @Override
  public void autonomousInit() {
    // Command auto = cf.getAutoCommand();

    // if (auto != null) {
    //   auto.schedule();
    // }
  }

  @Override
  public void autonomousPeriodic() {
    genralPeriodic();
  }

  @Override
  public void teleopInit() {
    // oi.setDefualtCommand(si.getDriveSubsys());
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
    genralPeriodic();
  }

  public void genralPeriodic() {
    
  }


  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("X", si.getDriveSubsys().getPose().getX());
    SmartDashboard.putNumber("Y", si.getDriveSubsys().getPose().getY());
  }

  // private void addVisionToEstimator() {
    
  // }
}