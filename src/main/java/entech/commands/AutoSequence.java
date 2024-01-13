package entech.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoSequence extends SequentialCommandGroup {
    private final Pose2d startingPose;

    public AutoSequence(Pose2d startingPose) {
        this.startingPose = startingPose;
    }

    public Pose2d getStartingPose() {
        return startingPose;
    }
}
