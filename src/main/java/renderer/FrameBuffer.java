package renderer;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    private int fboID = 0;
    private Texture texture = null;

    public FrameBuffer(int width, int height) {
        // Generate frame buffer
        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);

        // Create the texture to render the data to , and attach it to our framebuffer
        this.texture = new Texture(width, height);
    }
}
