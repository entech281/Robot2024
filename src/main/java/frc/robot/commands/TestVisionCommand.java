package frc.robot.commands;

import java.util.List;
import org.littletonrobotics.junction.Logger;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.vision.EntechTargetData;
import frc.robot.subsystems.vision.VisionSubsystem;

public class TestVisionCommand extends EntechCommand {
  private int stage = 0;
  private final VisionSubsystem vision;

  private int leftTestTag;
  private int rightTestTag;
  private int middleTestTag;

  public TestVisionCommand(VisionSubsystem vision) {
    super(vision);
    this.vision = vision;
  }

  @Override
  public void execute() {
    switch (stage) {
      case 0:
        testCamera(leftTestTag, RobotConstants.Vision.Cameras.LEFT, "left");
        break;
      case 1:
        testCamera(rightTestTag, RobotConstants.Vision.Cameras.RIGHT, "right");
        break;
      case 2:
        testCamera(middleTestTag, RobotConstants.Vision.Cameras.MIDDLE, "middle");
      default:
        break;
    }
  }

  private void testCamera(int id, String cameraName, String commonName) {
    EntechTargetData data = getDataOf(cameraName);
    if (data.getIds().contains(id)) {
      stage++;
    } else {
      Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
          "Put the tag with id " + id + " in front of the " + commonName + " camera.");
    }
  }

  private EntechTargetData getDataOf(String cameraName) {
    List<EntechTargetData> data = vision.getOutputs().getTargetsData();

    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getCameraName().equals(cameraName)) {
        return data.get(i);
      }
    }

    return null;
  }

  @Override
  public void end(boolean interrupted) {
    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
  }

  @Override
  public void initialize() {
    leftTestTag = (int) (1 + (Math.random() * 7));
    rightTestTag = (int) (1 + (Math.random() * 7));
    middleTestTag = (int) (1 + (Math.random() * 7));
    stage = 0;
  }

  @Override
  public boolean isFinished() {
    return stage >= 3;
  }
}