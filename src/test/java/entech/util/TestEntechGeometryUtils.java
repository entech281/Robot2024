package entech.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author aheitkamp
 */
public class TestEntechGeometryUtils {
    @Test
    public void testAveragePose2dCase1() {
        Pose2d a = new Pose2d(new Translation2d(1.0, 1.0), Rotation2d.fromDegrees(360));
        Pose2d b = new Pose2d(new Translation2d(0.0, 0.0), Rotation2d.fromDegrees(0));

        Pose2d result = EntechGeometryUtils.averagePose2d(a, b);

        assertEquals(result.getX(), 0.5);
        assertEquals(result.getY(), 0.5);
        assertEquals(result.getRotation().getDegrees(), 180);
    }
}
