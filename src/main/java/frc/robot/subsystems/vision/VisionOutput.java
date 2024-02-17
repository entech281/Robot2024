package frc.robot.subsystems.vision;

import java.util.List;
import java.util.Optional;

import org.littletonrobotics.junction.Logger;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import entech.subsystems.SubsystemOutput;


public class VisionOutput implements SubsystemOutput {
    public Optional<Double> timeStamp;
    public Optional<Pose2d> estimatedPose;
    public double latency;
    public int numberOfTargets;
    public boolean hasTargets;
    public List<PhotonTrackedTarget> targets;

    @Override
    public void log() {
        if (timeStamp.isPresent() && estimatedPose.isPresent()) {
            Logger.recordOutput("visionOutput/", timeStamp.get());
            Logger.recordOutput("visionOutput/", estimatedPose.get());
        }

        Logger.recordOutput("visionOutput/", latency);
        Logger.recordOutput("visionOutput/", numberOfTargets);
        Logger.recordOutput("visionOutput/", hasTargets);
        for (int i = 0; i < numberOfTargets; i++) {
            Logger.recordOutput("visionOutput/target" + i, targets.get(i));
        }
    }
}
