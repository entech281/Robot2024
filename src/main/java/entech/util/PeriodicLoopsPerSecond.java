package entech.util;

public class PeriodicLoopsPerSecond {

  private static double PERIODIC_LOOP_FACTOR_SEC = 50;

  public static double getLoopsPerSecond(double seconds) {
    return PERIODIC_LOOP_FACTOR_SEC * seconds;
  }
}
