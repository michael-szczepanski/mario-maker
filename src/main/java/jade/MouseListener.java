package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
  // GLFW Mouse callbacks

  private static MouseListener instance;
  private double scrollX, scrollY;
  private double xPos, yPos, lastY, lastX;
  private boolean[] mouseButtonPressed = new boolean[3];
  private boolean isDragging;

  private MouseListener() {
    this.scrollX = 0;
    this.scrollY = 0;
    this.xPos = 0;
    this.yPos = 0;
    this.lastX = 0;
    this.lastY = 0;
  }

  public static MouseListener get() {
    return MouseListener.instance == null ? new MouseListener() : MouseListener.instance;
  }

  public static void mousePosCallback(long window, double xPos, double yPos) {
    // sets previous xy coordinates to last positions, and updates current xy to previous xy
    get().lastX = get().xPos;
    get().lastY = get().yPos;
    get().xPos = xPos;
    get().yPos = yPos;
    get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
  }

  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    // mods are modifiers like holding shift or control during mouse press

    if (button < get().mouseButtonPressed.length) {
      if (action == GLFW_PRESS) {
        get().mouseButtonPressed[button] = true;
      } else if (action == GLFW_RELEASE) {
        get().mouseButtonPressed[button] = false;
        get().isDragging = false;
      }
    }
  }

  public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
    get().scrollX = xOffset;
    get().scrollY = yOffset;
  }

  public static void endFrame() {
    get().scrollX = 0;
    get().scrollY = 0;
    get().lastX = get().xPos;
    get().lastY = get().yPos;
  }

  public static double getX() {
    return get().xPos;
  }

  public static double getY() {
    return get().yPos;
  }

  public static double getDx() {
    return get().lastX - get().xPos;
  }

  public static double getDy() {
    return get().lastY - get().xPos;
  }

  public static double getScrollX() {
    return get().scrollX;
  }

  public static double getScrollY() {
    return get().scrollY;
  }

  public static boolean isDragging() {
    return get().isDragging;
  }

  public static boolean mouseButtonDown(int button) {
    return button < get().mouseButtonPressed.length && get().mouseButtonPressed[button];
  }
}
