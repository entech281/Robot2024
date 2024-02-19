package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose2d;
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.Optional;

public interface CameraContainerI {
  PhotonPipelineResult getFilteredResult();

  Optional<Pose2d> getEstimatedPose();

  double getLatency();

  boolean hasTargets();

  int getTargetCount();
}
