package frc.robot.operation;

import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import entech.util.EntechJoystick;

import java.util.Optional;

public class DebugController {


  public static final String DEBUG_CONTROLLER_NAME = "WhatIsThis";
  public static final int MAX_STICKS=3;

  public static Optional<EntechJoystick> findDebugController(){
    for ( int i=0;i<MAX_STICKS;i++){
      if (DriverStation.getJoystickName(i).equals(DEBUG_CONTROLLER_NAME)){
        return Optional.of( new EntechJoystick(i));
      }
    }
    return Optional.empty();
  }

  public static boolean isDebugControllerAvailable(){
     Optional<EntechJoystick> stick = findDebugController();
     return stick.isPresent();
  }
}
