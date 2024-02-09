package frc.robot.vision;

import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Pose2d;

public interface CameraContainerI {

    PhotonPipelineResult getFilteredResult();

    Optional<Pose2d> getEstimatedPose();

    double getLatency();

    boolean hasTargets();

    int getTargetCount();

}