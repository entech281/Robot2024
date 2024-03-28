package entech.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.wpi.first.math.util.Units;
import entech.util.AimCalculator.Target;

public class TestAimCalculator {
    private static final double TOLERANCE = 1;
    @Test
    void TestAim(){
        assertEquals(8, AimCalculator.getAngleDegreesFromDistance(Units.inchesToMeters(42), Target.SPEAKER), TOLERANCE);
        assertEquals(30, AimCalculator.getAngleDegreesFromDistance(Units.inchesToMeters(100), Target.SPEAKER),TOLERANCE);
    }
}
