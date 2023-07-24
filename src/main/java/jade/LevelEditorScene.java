package jade;

import org.lwjgl.BufferUtils;
import renderer.Shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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

  private float[] vertexArray = {
          // position                 // color
          // x      y     z             r     g     b     a
             0.5f, -0.5f, 0.0f,         1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
            -0.5f,  0.5f, 0.0f,         0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
             0.5f,  0.5f, 0.0f,         0.0f, 0.0f, 1.0f, 1.0f, // Top right    2
            -0.5f, -0.5f, 0.0f,         0.0f, 0.0f, 0.0f, 1.0f, // Bottom left  3
  };

  private int[] elementArray = {
          // IMPORTANT: Must mbe in counter-clockwise order
          /*
              1   2

              3   0
           */

          2, 1, 0, // Top right triangle
          0, 1, 3, // Bottom left triangle
  };

  private int vaoID, vboID, eboID;

  public LevelEditorScene() {

  }

  @Override
  public void init() {
    Shader testShader = new Shader("assets/shaders/default.glsl");

    // Compile and link shaders

    // Generate VAO, VBO, and EBO buffer objects, and send to GPU

    vaoID = glGenVertexArrays();
    glBindVertexArray(vaoID); // Everything after this line will be bound to vaoID

    // Create a float buffer of vertices
    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
    vertexBuffer.put(vertexArray).flip();

    // Create VBO and upload the vertex buffer
    vboID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW); // sends vertexBuffer to vboID and only draws it statically (no changes)

    // Create the indices and upload
    IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
    elementBuffer.put(elementArray).flip();

    eboID = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

    // Add the vertex attribute pointers
    int positionsSize = 3;
    int colorSize = 4;
    int floatSizeBytes = 4;
    int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
    glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
    glEnableVertexAttribArray(1);
  }

  @Override
  public void update(float dt) {
    // Bind shader program
    glUseProgram(shaderProgram);

    // Bind the VAO
    glBindVertexArray(vaoID);

    // Enable vertex attrib pointers
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

    // Unbind everything
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);
    glUseProgram(0);
  }
}
