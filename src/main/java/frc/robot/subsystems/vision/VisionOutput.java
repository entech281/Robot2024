package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose2d;
import entech.subsystems.SubsystemOutput;


public class VisionOutput implements SubsystemOutput {
  private Optional<Double> timeStamp;
  private Optional<Pose2d> estimatedPose;
  private double latency;
  private int numberOfTargets;
  private boolean hasTargets;
  private List<PhotonTrackedTarget> targets;

  @Override
  public void log() {
    if (timeStamp.isPresent() && estimatedPose.isPresent()) {
      Logger.recordOutput("visionOutput/timeStamp", timeStamp.get());
      Logger.recordOutput("visionOutput/estimatedPose", estimatedPose.get());
    }

    Logger.recordOutput("visionOutput/latency", latency);
    Logger.recordOutput("visionOutput/numberOfTargets", numberOfTargets);
    Logger.recordOutput("visionOutput/hasTargets", hasTargets);
    for (int i = 0; i < numberOfTargets; i++) {
      Logger.recordOutput("visionOutput/target" + i, targets.get(i));
    }
  }

  public Optional<Double> getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Optional<Double> timeStamp) {
    this.timeStamp = timeStamp;
  }

  public Optional<Pose2d> getEstimatedPose() {
    return this.estimatedPose;
  }

  public void setEstimatedPose(Optional<Pose2d> estimatedPose) {
    this.estimatedPose = estimatedPose;
  }

  public double getLatency() {
    return this.latency;
  }

  public void setLatency(double latency) {
    this.latency = latency;
  }

  public int getNumberOfTargets() {
    return this.numberOfTargets;
  }

  public void setNumberOfTargets(int numberOfTargets) {
    this.numberOfTargets = numberOfTargets;
  }

  public boolean isHasTargets() {
    return this.hasTargets;
  }

  public boolean getHasTargets() {
    return this.hasTargets;
  }

  public void setHasTargets(boolean hasTargets) {
    this.hasTargets = hasTargets;
  }

  public List<PhotonTrackedTarget> getTargets() {
    return this.targets;
  }

  public void setTargets(List<PhotonTrackedTarget> targets) {
    this.targets = targets;
  }
}
