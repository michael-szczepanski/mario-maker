#version 330 core
#type vertex
// a stands for attribute
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

out vec4 fColor;

void main() {
    fColor = aColor;
    // passes the position to fragment
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core

// This connects to out vec4 fColor on line 7
in vec4 fColor;

out vec4 color;

void main() {
    // colors the position with fColor passed in earlier and outs it to GPU
    color = fColor;
}