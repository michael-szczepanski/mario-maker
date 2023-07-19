package jade;

import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene {

  private String vertexShaderSrc = "#version 330 core\n" +
          "layout (location=0) in vec3 aPos;\n" +
          "layout (location=1) in vec4 aColor;\n" +
          "\n" +
          "out vec4 fColor;\n" +
          "\n" +
          "void main() {\n" +
          "    fColor = aColor;\n" +
          "    // passes the position to fragment\n" +
          "    gl_Position = vec4(aPos, 1.0);\n" +
          "}";

  private String fragmentShaderSrc = "#version 330 core\n" +
          "\n" +
          "in vec4 fColor;\n" +
          "\n" +
          "out vec4 color;\n" +
          "\n" +
          "void main() {\n" +
          "    // colors the position with fColor passed in earlier and outs it to GPU\n" +
          "    color = fColor;\n" +
          "}";

  private int vertexID, fragmentID, shaderProgram;

  public LevelEditorScene() {

  }

  @Override
  public void init() {
    // Compile and link shaders

    // Load and compile vertex shader
    vertexID = glCreateShader(GL_VERTEX_SHADER);
    // Pass the shader source code to the GPU
    glShaderSource(vertexID, vertexShaderSrc);
    glCompileShader(vertexID);

    // Check for errors in compilation
    int success = glGetShaderi(vertexID, GL_COMPILE_STATUS); // 0 on fail

    if (success == GL_FALSE) {
      int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
      System.out.println("Error: 'defaultShader.glsl'\n\tVertex shader compilation failed.");
      System.out.println(glGetShaderInfoLog(vertexID, len));
      assert false : "";
    }

    // Load and compile fragment shader
    fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
    // Pass the shader source code to the GPU
    glShaderSource(fragmentID, fragmentShaderSrc);
    glCompileShader(fragmentID);

    // Check for errors in compilation
    success = glGetShaderi(fragmentID, GL_COMPILE_STATUS); // 0 on fail

    if (success == GL_FALSE) {
      int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
      System.out.println("Error: 'defaultShader.glsl'\n\tFragment shader compilation failed.");
      System.out.println(glGetShaderInfoLog(fragmentID, len));
      assert false : "";
    }

    // Link shaders and check for errors
    shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexID);
    glAttachShader(shaderProgram, fragmentID);
    glLinkProgram(shaderProgram);

    // Check for linking errors
    success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
    if (success == GL_FALSE) {
      int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
      System.out.println("Error: 'defaultShader.glsl'\n\tShader link failed.");
      System.out.println(glGetProgramInfoLog(shaderProgram, len));
      assert false : "";
    }
  }

  @Override
  public void update(float dt) {

  }
}
