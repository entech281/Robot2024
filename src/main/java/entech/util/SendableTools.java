package entech.util;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.SendableBuilder;

public final class SendableTools {

    private static String formatPose2d(Supplier<Pose2d> supplier) {
        return "(" + supplier.get().getX() + ", " + supplier.get().getY() + ")";
    }

    public static void sendPose2d(SendableBuilder builder, String key, Supplier<Pose2d> supplier) {
        builder.addStringProperty(key, () -> {
            return formatPose2d(supplier);
        }, null);
    }

    public static void sendDouble(SendableBuilder builder, String key, DoubleSupplier supplier) {
        builder.addDoubleProperty(key, supplier, null);
    }

    public static void sendString(SendableBuilder builder, String key, Supplier<String> supplier) {
        builder.addStringProperty(key, supplier, null);
    }

    public static void sendBoolean(SendableBuilder builder, String key, BooleanSupplier supplier) {
        builder.addBooleanProperty(key, supplier, null);
    }

    public static void sendInt(SendableBuilder builder, String key, LongSupplier supplier) {
        builder.addIntegerProperty(key, supplier, null);
    }
}
