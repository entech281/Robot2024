package frc.robot.processors.filters;

import frc.robot.subsystems.DriveInput;

public interface DriveFilterI {
    public DriveInput process(DriveInput input);
}
