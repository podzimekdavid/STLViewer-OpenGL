#version 150
in vec3 inPosition;
in vec3 inNormal;


uniform mat4 view;
uniform mat4 projection;


uniform mat4 lightVP;
uniform vec3 lightPosition;
uniform vec3 eyePosition;
uniform mat4 model;

out vec3 normal;
out vec3 light;
out vec3 viewDirection;
out vec4 depthTextureCoord;
out vec3 positionA;
out float dist;

const float PI = 3.14159;



void main() {
    vec3 position = inPosition * 2 - 1;

    vec3 pos3;
    mat3 norm=transpose(inverse(mat3(view*model)));

    pos3=position;
    normal = inNormal;

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
