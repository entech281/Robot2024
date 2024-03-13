package frc.robot.processors;

import java.util.ArrayList;
import java.util.List;
import frc.robot.io.RobotIO;
import frc.robot.processors.filters.DriveFilterI;
import frc.robot.processors.filters.MaxConstraintFilter;
import frc.robot.processors.filters.NoteAlignmentFilter;
import frc.robot.processors.filters.SquaringFilter;
import frc.robot.processors.filters.TwistFilter;
import frc.robot.subsystems.drive.DriveInput;

public class DriveInputProcessor {
  private final List<DriveFilterI> driveFilters = new ArrayList<>();

  public DriveInputProcessor() {
    driveFilters.add(new TwistFilter());
    driveFilters.add(new SquaringFilter());
    driveFilters.add(new MaxConstraintFilter());
    // driveFilters.add(new AutoYawFilter());
    driveFilters.add(new NoteAlignmentFilter());
  }

  public DriveInput processInput(DriveInput input) {
    DriveInput processedInput = new DriveInput(input);

    for (DriveFilterI filter : driveFilters) {
      processedInput = filter.process(processedInput);
      processedInput.setKey(filter.getClass().getSimpleName());
      RobotIO.processInput(processedInput);
    }

    processedInput.setKey("final");
    RobotIO.processInput(processedInput);

    return processedInput;
  }
}
