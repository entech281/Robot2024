package frc.robot.commands.test;

import java.util.List;
import org.littletonrobotics.junction.Logger;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.vision.EntechTargetData;
import frc.robot.subsystems.vision.VisionSubsystem;

public class TestVisionCommand extends EntechCommand {
  private static final int TEST_TAG = 1;
  private int stage = 0;
  private final VisionSubsystem vision;

  public TestVisionCommand(VisionSubsystem vision) {
    super(vision);
    this.vision = vision;
  }

  @Override
  public void execute() {
    switch (stage) {
      case 0:
        testCamera(RobotConstants.Vision.Cameras.LEFT, "left");
        break;
      case 1:
        testCamera(RobotConstants.Vision.Cameras.RIGHT, "right");
        break;
      default:
        break;
    }
  }

  private void testCamera(String cameraName, String commonName) {
    EntechTargetData data = getDataOf(cameraName);
    if (data == null) {
      Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
          "The " + cameraName + " camera was not found check coprocessors.");
      return;
    }

    if (data.getIds().contains(TEST_TAG)) {
      stage++;
    } else {
      Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
          "Put the tag with id " + TEST_TAG + " in front of the " + commonName + " camera.");
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
    stage = 0;
  }

  @Override
  public boolean isFinished() {
    return stage >= 2;
  }
}
