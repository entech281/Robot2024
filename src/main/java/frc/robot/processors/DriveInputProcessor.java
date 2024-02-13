package frc.robot.processors;

import java.util.ArrayList;
import java.util.List;

import frc.robot.processors.filters.DriveFilterI;
import frc.robot.processors.filters.MaxConstraintFilter;
import frc.robot.processors.filters.SquaringFilter;
import frc.robot.processors.filters.TwistFilter;
import frc.robot.subsystems.DriveInput;

public class DriveInputProcessor {
    private final List<DriveFilterI> driveFilters = new ArrayList<>();

    public DriveInputProcessor() {
        driveFilters.add(new TwistFilter());
        driveFilters.add(new SquaringFilter());
        driveFilters.add(new MaxConstraintFilter());
    }

    public DriveInput processInput(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.pose = input.pose;
        processedInput.rot = input.rot;
        processedInput.xSpeed = input.xSpeed;
        processedInput.ySpeed = input.ySpeed;

        for (DriveFilterI filter : driveFilters) {
            processedInput = filter.process(processedInput);
        }

        return processedInput;
    }
}
