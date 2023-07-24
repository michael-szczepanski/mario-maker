package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader {
  private int shaderProgramID;

  private String vertexSource;
  private String fragmentSource;
  private String filepath;

  public Shader(String filepath) {
    this.filepath = filepath;

    try {
      // Splits shader file by #types
      String source = new String(Files.readAllBytes(Paths.get(filepath)));
      String[] splitString = source.split("(#type)( )+([a-zA-z]+)");

      // Find first pattern after #type 'pattern'
      int index = source.indexOf("#type") + 6;
      int eol = source.indexOf("\r\n", index);
      String firstPattern = source.substring(index, eol).trim();

      // Find second pattern after #type 'pattern'
      index = source.indexOf("#type", eol) + 6;
      eol = source.indexOf("\r\n", index);
      String secondPattern = source.substring(index, eol).trim();

      if (firstPattern.equals("vertex")) {
        vertexSource = splitString[1];
      } else if (firstPattern.equals("fragment")) {
        fragmentSource = splitString[1];
      } else {
        throw new IOException(String.format("Unexpected token '%s''\r\n", firstPattern));
      }

      if (secondPattern.equals("vertex")) {
        vertexSource = splitString[1];
      } else if (secondPattern.equals("fragment")) {
        fragmentSource = splitString[1];
      } else {
        throw new IOException(String.format("Unexpected token '%s''\r\n", secondPattern));
      }

    } catch (IOException e) {
      e.printStackTrace();
      assert false : "Error: Could not open file for shader: '" + filepath + "'";
    }
  }

  public void compile() {

  }

  public void use() {

  }
  public void detach() {

  }
}
