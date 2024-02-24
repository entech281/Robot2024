package entech.util;

public class PeriodicLoopsPerSecond {

  private static int PERIODIC_LOOP_FACTOR_SEC = 50;

  public static int getLoopsPerSecond(int seconds) {
    return PERIODIC_LOOP_FACTOR_SEC / seconds;
  }
}
