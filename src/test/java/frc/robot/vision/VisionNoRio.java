package frc.robot.vision;

import org.junit.jupiter.api.Test;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.pose.CameraContainer;

import java.io.IOException;
import java.util.Optional;

public class VisionNoRio {
	
	@Test
	public void testRunningCamera() throws Exception{	
        NetworkTableInstance nt = NetworkTableInstance.create();
        nt.setServer("10.2.81.151");
        nt.startClient4("CAMERATEST");

        AprilTagFieldLayout photonAprilTagFieldLayout;
        try {
            photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource("/Bedroom2024AprilTags.json");
            System.out.println(photonAprilTagFieldLayout.getTagPose(1).get());
            System.out.println(photonAprilTagFieldLayout.getTagPose(2).get());
            System.out.println(photonAprilTagFieldLayout.getTagPose(3).get());
        } catch (IOException e) {
            throw new RuntimeException("Could not load wpilib AprilTagFields");
        }

		CameraContainer camera = new CameraContainer("Arducam_OV9281_USB_Camera", new Transform3d(), photonAprilTagFieldLayout, null, nt);
		
		while ( true ) {
            System.out.println("Latency: " + camera.getLatency());
			if ( camera.hasTargets() ) {
                System.out.println("# of targets: " + camera.getTargetCount());
				Optional<Pose3d> estPose = camera.getEstimatedPose();
				if ( estPose.isPresent() ) {
                    System.out.println(estPose.get());
				}
				else {
					System.out.println("No Pose Estimate");
				}
				System.out.println("Area: " + camera.getResult().getBestTarget().getArea());
				System.out.println("Ambiguity: " + camera.getResult().getBestTarget().getPoseAmbiguity());
			}
			else {
				System.out.println("No Targets");
			}

			Thread.sleep(1000);
		}
	}

}
