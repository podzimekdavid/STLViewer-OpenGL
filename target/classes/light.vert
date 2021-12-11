#version 150
in vec3 inPosition;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;


void main() {

   vec3 position = inPosition * 2 - 1;
 /*
    vec3 pos3=position;*/

    gl_Position =projection * view *model* vec4(position, 1.0);
}
