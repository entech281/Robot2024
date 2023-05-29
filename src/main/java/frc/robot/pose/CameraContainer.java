package frc.robot.pose;

import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;

public class CameraContainer {
    private PhotonCamera camera;
    private PhotonPoseEstimator estimator;

    public CameraContainer(String cameraName, Transform3d robotToCamera, AprilTagFieldLayout fieldLayout) {
      camera = new PhotonCamera(cameraName);
      estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, robotToCamera);
      estimator.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
    }

    public PhotonPipelineResult getLatestResult() {
      return camera.getLatestResult();
    }

    public Optional<EstimatedRobotPose> getEstimatedPose() {
      return estimator.update();
    }
  }
