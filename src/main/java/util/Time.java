package util;

public class Time {
  // static vars are initialized at application startup
  public static float timeStarted = System.nanoTime();

  public static float getTime() {
    // Will return time in seconds since the time application started
    return (float)((System.nanoTime() - timeStarted) * 1E-9);
  }
}
