package frc.robot.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.processors.filters.DriveFilterI;
import frc.robot.processors.filters.MaxConstraintFilter;
import frc.robot.processors.filters.SquaringFilter;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;

public class NoteAlignProcessor {
  private List<DriveFilterI> driveFilters = new ArrayList<>();
  
  public NoteAlignProcessor() {
    driveFilters.add(new SquaringFilter());
    driveFilters.add(new MaxConstraintFilter());
  }

  public DriveInput driveProcessInput(DriveInput input) {
    DriveInput processedInput = new DriveInput(input);
    for (DriveFilterI filter : driveFilters) {
      processedInput = filter.process(processedInput);
      processedInput.setKey(filter.getClass().getSimpleName());
      RobotIO.processInput(processedInput);
    }

    return processedInput;
  }

  public DriveInput keepAligned(DriveInput di, NoteDetectorOutput no) {
    double noteOffset = -no.getCenterOfClosestNote().get().x;
    
    return di;
  }
  
}
