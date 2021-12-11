#version 150
in vec3 inPosition;


uniform mat4 view;
uniform mat4 projection;


uniform mat4 lightVP;

uniform int solid;
uniform vec3 lightPosition;
uniform vec3 eyePosition;
uniform float time;
uniform vec3 offset;
uniform mat4 model;

out vec2 texCoord;
out vec3 normal;
out vec3 light;
out vec3 viewDirection;
out vec4 depthTextureCoord;
out vec3 positionA;
out float dist;

const float PI = 3.14159;

vec3 getSphere(vec2 pos) {
    float az = pos.x * PI;
    float ze = pos.y * PI / 2;
    float r = 0.9;

    float x = r * cos(az) * cos(ze);
    float y = r * sin(az) * cos(ze);
    float z = r * sin(ze);

    return vec3(x, y, z);
}

vec3 getSphSolid(vec2 pos) {
    float az = ((pos.x +1)/2)*PI;
    float ze = ((pos.y +1)/2)* 2*PI;
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

vec3 getSphSolidNormal(vec2 pos) {
    vec3 u = getSphSolid(pos + vec2(0.001, 0)) - getSphSolid(pos - vec2(0.001, 0));
    vec3 v = getSphSolid(pos + vec2(0, 0.001)) - getSphSolid(pos - vec2(0, 0.001));
    return cross(u, v);
}

vec3 getSphESolidNormal(vec2 pos) {
    vec3 u = getSphESolid(pos + vec2(0.001, 0)) - getSphESolid(pos - vec2(0.001, 0));
    vec3 v = getSphESolid(pos + vec2(0, 0.001)) - getSphESolid(pos - vec2(0, 0.001));
    return cross(u, v);
}



vec3 getMove(vec2 pos) {

    return vec3((pos.x+offset.x) * 3.0, (pos.y+offset.y)*3.0, 1.0);
}

vec3 getMoveNormal(vec2 pos) {
    vec3 u = getMove(pos + vec2(0.001, 0)) - getMove(pos - vec2(0.001, 0));
    vec3 v = getMove(pos + vec2(0, 0.001)) - getMove(pos - vec2(0, 0.001));
    return cross(u, v);
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
vec3 getSolidType4Normal(vec2 pos) {
    vec3 u = getSolidType4(pos + vec2(0.001, 0)) - getSolidType4(pos - vec2(0.001, 0));
    vec3 v = getSolidType4(pos + vec2(0, 0.001)) - getSolidType4(pos - vec2(0, 0.001));
    return cross(u, v);
}

vec3 getSphereNormal(vec2 pos) {
    vec3 u = getSphere(pos + vec2(0.001, 0)) - getSphere(pos - vec2(0.001, 0));
    vec3 v = getSphere(pos + vec2(0, 0.001)) - getSphere(pos - vec2(0, 0.001));
    return cross(u, v);
}

vec3 getPlane(vec2 pos) {
    return vec3(pos * 3.0, -1.0);
}

vec3 getTimePlane(vec2 pos) {
    return vec3(pos * 3.0, -1*cos(time+pos.x*3));
}

vec3 getTimePlaneNormal(vec2 pos) {
    vec3 u = getTimePlane(pos + vec2(0.001, 0)) - getTimePlane(pos - vec2(0.001, 0));
    vec3 v = getTimePlane(pos + vec2(0, 0.001)) - getTimePlane(pos - vec2(0, 0.001));
    return cross(u, v);
}

vec3 getPlaneNormal(vec2 pos) {
    vec3 u = getPlane(pos + vec2(0.001, 0)) - getPlane(pos - vec2(0.001, 0));
    vec3 v = getPlane(pos + vec2(0, 0.001)) - getPlane(pos - vec2(0, 0.001));
    return cross(u, v);
}

float getZ(vec2 pos) {
    return sin(pos.x * 5);
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

vec3 getParabolicSolidNormal(vec2 pos) {
    vec3 u = getParabolicSolid(pos + vec2(0.001, 0)) - getParabolicSolid(pos - vec2(0.001, 0));
    vec3 v = getParabolicSolid(pos + vec2(0, 0.001)) - getParabolicSolid(pos - vec2(0, 0.001));
    return cross(u, v);
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

vec3 getDonutSolidNormal(vec2 pos) {
    vec3 u = getDonutSolid(pos + vec2(0.001, 0)) - getDonutSolid(pos - vec2(0.001, 0));
    vec3 v = getDonutSolid(pos + vec2(0, 0.001)) - getDonutSolid(pos - vec2(0, 0.001));
    return cross(u, v);
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

vec3 getWaveSolidNormal(vec2 pos) {
    vec3 u = getWaveSolid(pos + vec2(0.001, 0)) - getWaveSolid(pos - vec2(0.001, 0));
    vec3 v = getWaveSolid(pos + vec2(0, 0.001)) - getWaveSolid(pos - vec2(0, 0.001));
    return cross(u, v);
}

void main() {
    /*vec2 position = inPosition * 2 - 1;
    texCoord = inPosition;*/

    vec3 pos3;
    mat3 norm=transpose(inverse(mat3(view*model)));

    /*switch (solid){
        case 1:
        case 6:
        pos3 = getSphere(position);
        normal = getSphereNormal(position);
        break;
        case 2:
        pos3 = getPlane(position);
        normal = getPlaneNormal(position);
        break;
        case 3:
        pos3=getTimePlane(position);
        normal = getTimePlaneNormal(position);
        break;
        case 4:
        pos3=getSolidType4(position);
        normal=getSolidType4Normal(position);
        break;
        case 5:
        pos3 = getMove(position);
        normal = getMoveNormal(position);
        break;
        case 7:
        pos3=getParabolicSolid(position);
        normal=getParabolicSolidNormal(position);
        break;
        case 8:
        pos3=getDonutSolid(position);
        normal=getDonutSolidNormal(position);
        break;
        case 9:
        pos3=getWaveSolid(position);
        normal=getWaveSolidNormal(position);
        break;
        case 10:
        pos3=getSphSolid(position);
        normal=getSphSolid(position);
        break;
        case 11:
        pos3=getSphESolid(position);
        normal=getSphESolid(position);
        break;
        case 12:
        pos3=stl;
        normal = stl;
        break;
    }*/

    pos3=inPosition;

    normal = norm*normal;

    positionA=pos3;
    gl_Position = projection * view *model*vec4(pos3, 1.0);

    light = lightPosition - pos3;
    viewDirection = eyePosition - pos3;

    depthTextureCoord = lightVP * vec4(pos3, 1.0);
    depthTextureCoord.xyz = depthTextureCoord.xyz / depthTextureCoord.w;
    depthTextureCoord.xyz = (depthTextureCoord.xyz + 1) / 2;

    dist=length(light);
} 
