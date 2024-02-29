package frc.robot.processors;

import java.util.ArrayList;
import java.util.List;
import frc.robot.io.RobotIO;
import frc.robot.processors.filters.DriveFilterI;
import frc.robot.processors.filters.MaxConstraintFilter;
import frc.robot.processors.filters.NoteAlignmentFilter;
import frc.robot.processors.filters.SquaringFilter;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;

public class NoteAlignProcessor {
  private List<DriveFilterI> filters = new ArrayList<>();
  private NoteAlignmentFilter naf = new NoteAlignmentFilter();

  public NoteAlignProcessor() {
    filters.add(new SquaringFilter());
    filters.add(new MaxConstraintFilter());
  }

  public DriveInput driveProcessInput(DriveInput input) {
    DriveInput processedInput = new DriveInput(input);
    for (DriveFilterI filter : filters) {
      processedInput = filter.process(processedInput);
      processedInput.setKey(filter.getClass().getSimpleName());
      RobotIO.processInput(processedInput);
    }

    return processedInput;
  }

  public DriveInput keepAligned(DriveInput di, NoteDetectorOutput no) {
    DriveInput processedInput = new DriveInput(di);
    processedInput = naf.process(processedInput);
    processedInput.setKey(naf.getClass().getSimpleName());
    RobotIO.processInput(processedInput);
    return processedInput;
  }
}
