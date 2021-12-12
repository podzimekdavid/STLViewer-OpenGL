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
out float dist;


void main() {
    vec3 pos3 = inPosition * 2 - 1;
    mat3 norm = transpose(inverse(mat3(view*model)));

    normal = inNormal;
    normal = norm*normal;


    light = lightPosition - pos3;
    viewDirection = eyePosition - pos3;

    depthTextureCoord = lightVP * vec4(pos3, 1.0);
    depthTextureCoord.xyz = depthTextureCoord.xyz / depthTextureCoord.w;
    depthTextureCoord.xyz = (depthTextureCoord.xyz + 1) / 2;

    dist = length(light);

    gl_Position = projection * view *model*vec4(pos3, 1.0);
} 
