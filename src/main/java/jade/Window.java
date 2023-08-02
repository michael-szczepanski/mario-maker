package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow;
  private ImGuiLayer imGuiLayer;

  public float r, g, b, a;

  private static Window instance = null;

  private static Scene currentScene;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario Maker";
    r = 1;
    g = 1;
    b = 1;
    a = 1;
  }

  public static void changeScene(int newScene) {
    switch (newScene) {
      case 0:
        currentScene = new LevelEditorScene();
        currentScene.init();
        currentScene.start();
        break;
      case 1:
        currentScene = new LevelScene();
        currentScene.init();
        currentScene.start();
        break;
      default:
        assert false : String.format("Unknown scene '%s'", newScene);
        break;
    }
  }

  public static Window get() {
    if (Window.instance == null) {
      instance = new Window();
    }

    return Window.instance;
  }

  public static Scene getScene() {
    return get().currentScene;
  }

  public void run() {
    System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    init();
    loop();

    // Free memory after the loop
    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  public void init() {
    // Setup an error callback
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW.");
    }

    // Configure GLFW
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Window is not visible at first

    // Create the window
    glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
    if (glfwWindow == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window.");
    }

    // :: is a shortcut for lambda function e.g. (x) -> x + 2
    // it forwards the position callback to MouseListener callback function

    // TODO: gamepad listener callback
    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
    glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
      Window.setWidth(newWidth);
      Window.setHeight(newHeight);
    });

    // Make the OpenGL context current
    glfwMakeContextCurrent(glfwWindow);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(glfwWindow);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    glEnable(GL_BLEND);
    glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    this.imGuiLayer = new ImGuiLayer(glfwWindow);
    this.imGuiLayer.initImGui();

    Window.changeScene(0);
  }

  public void loop() {
    float beginTime = (float)glfwGetTime();
    float endTime;
    float dt = -1.0f;

    currentScene.load();

    while (!glfwWindowShouldClose(glfwWindow)) {
      // Poll events
      glfwPollEvents();

      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT);

      if (dt >= 0) {
        currentScene.update(dt);
      }

      this.imGuiLayer.update(dt, currentScene);
      glfwSwapBuffers(glfwWindow);

      endTime = (float)glfwGetTime();
      dt = endTime - beginTime;
      beginTime = endTime;
    }

    currentScene.saveExit();
  }

  public static int getWidth() {
    return get().width;
  }

  public static int getHeight() {
    return get().height;
  }

  public static void setWidth(int newWidth) {
    get().width = newWidth;
  }

  public static void setHeight(int newHeight) {
    get().height = newHeight;
  }
}