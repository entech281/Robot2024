package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


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
    generalPeriodic();
  }

  @Override
  public void teleopInit() {
    oi.setDefaults();
  }

  @Override
  public void disabledInit() {
    generalPeriodic();
  }

  @Override
  public void teleopPeriodic(){
  }

  @Override
  public void disabledPeriodic() {
    generalPeriodic();
  }

  public void generalPeriodic() {
    
  }


  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("X", si.getDriveSubsys().getPose().getX());
    SmartDashboard.putNumber("Y", si.getDriveSubsys().getPose().getY());
    SmartDashboard.putString("Current Drive Command", si.getDriveSubsys().getCurrentCommand() + "");
    SmartDashboard.putString("Default Drive Command", si.getDriveSubsys().getDefaultCommand() + "");
  }

  // private void addVisionToEstimator() {
    
  // }
}