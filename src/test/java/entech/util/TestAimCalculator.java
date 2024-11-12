package entech.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.wpi.first.math.util.Units;
import frc.entech.util.AimCalculator;
import frc.entech.util.AimCalculator.Target;

class TestAimCalculator {
    private static final double TOLERANCE = 1.25;

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
        assertEquals(8, AimCalculator.getPivotAngleFromDistance(1.22), TOLERANCE);
        assertEquals(18, AimCalculator.getPivotAngleFromDistance(1.83), TOLERANCE);
        assertEquals(26, AimCalculator.getPivotAngleFromDistance(2.44), TOLERANCE);
        assertEquals(32, AimCalculator.getPivotAngleFromDistance(3), TOLERANCE);
    }
}
