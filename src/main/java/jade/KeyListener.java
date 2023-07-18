package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
  private static KeyListener instance;
  private boolean[] keyPressed = new boolean[350];

  private KeyListener() {

  }

  public static KeyListener get() {
    return KeyListener.instance == null ? new KeyListener() : KeyListener.instance;
  }

  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    if (action == GLFW_PRESS) {
      get().keyPressed[key] = true;
    } else if (action == GLFW_RELEASE) {
      get().keyPressed[key] = false;
    }
  }

  public static boolean isKeyPressed(int keyCode) {
    return keyCode < get().keyPressed.length && get().keyPressed[keyCode];
  }
}
