#version 150
in vec2 inPosition;// input from the vertex buffer

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform int solid;
uniform float time;
uniform vec3 offset;

const float PI = 3.14159;

vec3 getSphSolid(vec2 pos) {
    float az = ((pos.x +1)/2)* PI;
    float ze = ((pos.y +1)/2)*2* PI;
    float r = cos(4*az);

    float x = r * cos(az) * cos(ze);
    float y = r * sin(az) * cos(ze);
    float z = r * sin(ze);

    return vec3(x, y, z);
}

vec3 getSphESolid(vec2 pos) {
    float az = ((pos.x +1)/2)*2*PI;
    float ze = ((pos.y +1)/2)*PI;
    float r = cos(4*az);

    float x = r * cos(az) * cos(ze);
    float y = r * sin(az) * cos(ze);
    float z = r * sin(ze);

    return vec3(x, y, z);
}

vec3 getSphere(vec2 pos) {
    float az = pos.x * PI;
    float ze = pos.y * PI / 2;
    float r = 0.9;

    float x = r * cos(az) * cos(ze);
    float y = r * sin(az) * cos(ze);
    float z = r * sin(ze);

    return vec3(x, y, z);
}

vec3 getMove(vec2 pos) {
    return vec3((pos.x+offset.x) * 3.0, (pos.y+offset.y)*3.0, 1.0);
}

vec3 getPlane(vec2 pos) {
    return vec3(pos * 3.0, -1.0);
}

vec3 getTimePlane(vec2 pos) {
    return vec3(pos* 3.0, -1*cos(time+pos.x*3));
}

vec3 getSolidType4(vec2 pos) {
    float az = pos.x * PI;
    float ze = pos.y * PI;
    float r = 0.3;

    float x = r * az;
    float y = r * ze;
    float z = r * cos(sqrt(20*(az*az)+20*(ze*ze)));

    return vec3(x, y, z);
}

vec3 getParabolicSolid(vec2 pos){
    float u = pos.x*PI;
    float v = pos.y*PI;
    float r = 0.6;

    float x = r*cos(u);
    float y = r*sin(u);
    float z = v/2;

    return vec3(x, y, z);
}

vec3 getDonutSolid(vec2 pos){
    float u = pos.x*PI;
    float v = pos.y*PI;
    float a =1;
    float b = 0.3;

    float x = cos(u)*(a+b*cos(v));
    float y = sin(u)*(a + b*cos(v));
    float z = b*sin(v);

    return vec3(x, y, z);
}

vec3 getWaveSolid(vec2 pos){
    float u = pos.x*PI;
    float v = pos.y*PI;
    float a =2;

    float x = u;
    float y = v;
    float z = -(-(u*u)-(v*v)+a);

    return vec3(x, y, z);
}

void main() {

    vec2 position = inPosition * 2 - 1;

    vec3 pos3;
    switch (solid){
        case 1:
        pos3 = getSphere(position);
        break;
        case 2:
        pos3 = getPlane(position);
        break;
        case 3:
        pos3=getTimePlane(position);
        break;
        case 4:
        pos3=getSolidType4(position);
        break;
        case 5:
        pos3 = getMove(position);
        break;
        case 7:
        pos3=getParabolicSolid(position);
        break;
        case 8:
        pos3=getDonutSolid(position);
        break;
        case 9:
        pos3=getWaveSolid(position);
        break;
        case 10:
        pos3=getSphSolid(position);
        break;
        case 11:
        pos3=getSphESolid(position);
        break;
    }

    gl_Position =projection * view *model* vec4(pos3, 1.0);
}
