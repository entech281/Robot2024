package frc.robot.vision;

import java.io.IOException;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionRunner {

    public static void main(String[] args) {
        NetworkTableInstance nt = NetworkTableInstance.create();
        nt.setServer("10.2.81.100");
        nt.startClient4("CAMERATEST");

        AprilTagFieldLayout photonAprilTagFieldLayout;
        try {
            photonAprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load wpilib AprilTagFields");
        }

        CameraContainer camera = new CameraContainer("Arducam_OV9281_USB_Camera", new Transform3d(),
                photonAprilTagFieldLayout, null);

        while (true) {
            if (camera.hasTargets()) {
                System.out.println(camera.getEstimatedPose());
            } else {
                System.out.println("No Targets");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}
