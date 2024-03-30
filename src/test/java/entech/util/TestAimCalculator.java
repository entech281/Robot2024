package entech.util;

import static org.junit.jupiter.api.Assertions.*;
import edu.wpi.first.math.util.Units;
import entech.util.AimCalculator.Target;
import frc.robot.RobotConstants;
import org.junit.jupiter.api.Test;

public class TestAimCalculator {
    private static final double TOLERANCE = 1.25;
    private static final double HALF_FRAME = RobotConstants.AUTO_AIM.DISTANCE_FROM_REAR_TO_CENTER;

    @Test
    void TestSadraAim() {
        assertEquals(8,
                AimCalculator.getAngleDegreesFromDistance(Units.inchesToMeters(42), Target.SPEAKER),
                TOLERANCE);
        assertEquals(30, AimCalculator.getAngleDegreesFromDistance(Units.inchesToMeters(100),
                Target.SPEAKER), TOLERANCE);
    }

    @Test
    void TestJohnAim() {
        assertEquals(15, AimCalculator.getPivotAngleFromDistance(48 - HALF_FRAME), TOLERANCE);
        assertEquals(25, AimCalculator.getPivotAngleFromDistance(72 - HALF_FRAME), TOLERANCE);
        assertEquals(30, AimCalculator.getPivotAngleFromDistance(96 - HALF_FRAME), TOLERANCE);
        assertEquals(34, AimCalculator.getPivotAngleFromDistance(120 - HALF_FRAME), TOLERANCE);
    }
}
