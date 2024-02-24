package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.vision.VisionSubsystem;

public class TestVisionCommand extends EntechCommand {
  private int stage = 0;

  public TestVisionCommand(VisionSubsystem vision) {

  }

  @Override
  public void execute() {
    switch (stage) {

    }
  }

  @Override
  public void initialize() {
    stage = 0;
  }

  @Override
  public boolean isFinished() {
    return stage >= 5;
  }
}
