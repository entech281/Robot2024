package frc.robot.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import entech.util.EntechGeometryUtils;
import frc.robot.RobotConstants;

public class TwoCameras {
    private final CameraContainer c1;
    private final CameraContainer c2;

    public TwoCameras(CameraContainer camera1, CameraContainer camera2){
        c1 = camera1;
        c2 = camera2;
    }

    public void runTwoCameras() {
        boolean c1HasTarget = c1.hasTargets();
        boolean c2HasTarget = c2.hasTargets();

        if (c1HasTarget) {
            System.out.print("Alpha has target");
        } else {
            System.out.print("Alpha has no target");
        }

        if (c2HasTarget) {
            System.out.print("Bravo has target");
        } else {
            System.out.print("Bravo has no target");
        }

        if (c1HasTarget || c2HasTarget) {
            Optional<Pose3d> estPose1 = c1.getEstimatedPose();
            Optional<Pose3d> estPose2 = c2.getEstimatedPose();
            if (c1HasTarget) {
                if (estPose1.isPresent()) {
                    System.out.println(estPose1.get());
                } else {
                    System.out.println("No Pose Estimate Alpha");
                }
            }
            if (c2HasTarget) {
                if (estPose2.isPresent()) {
                    System.out.println(estPose2.get());
                } else {
                    System.out.println("No Pose Estimate Bravo");
                }
            }
            if (c1HasTarget && c2HasTarget) {
                if (estPose1.isPresent() && estPose2.isPresent()) {
                    System.out.println();
                } else {
                    System.out.println("No Pose Estimate both");
                }
            }
        }
        System.out.println();
    }
}
