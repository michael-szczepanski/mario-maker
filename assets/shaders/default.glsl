#type vertex
#version 330 core
// a stands for attribute
// in expects the variables to come in through the VAO
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

// uniforms are values that stick around independent of inidividual objects
// e.q. camera position
// uProjection and uView will not change when we bind a new VAO
uniform mat4 uProjection;
uniform mat4 uView;

// out means that a variable will be output and specified in the main()
// every out needs a corresponding in in the fragment shader
out vec4 fColor;

// This is the entry point of the program
void main() {
    fColor = aColor;
    // passes the position to fragment
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

uniform float uTime;

// This connects to out vec4 fColor on line 7
in vec4 fColor;

out vec4 color;

void main() {
    color = fColor;
}