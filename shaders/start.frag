#version 150
in vec2 texCoord;
in vec3 normal;
in vec3 light;
in vec3 viewDirection;
in vec4 depthTextureCoord;
in vec3 positionA;
in float dist;

uniform sampler2D depthTexture;
uniform sampler2D imageTexture;
uniform int colorMode;

uniform int amb;
uniform int dif;
uniform int spc;

uniform float constantAttenuation, linearAttenuation, quadraticAttenuation;

out vec4 outColor;

void main() {
    vec3 ambient = vec3(0.5)*amb;

    float NdotL = max(0, dot(normalize(light), normalize(normal)));
    vec3 diffuse = vec3(NdotL * vec3(0.5))*dif;

    vec3 halfVector = normalize(light + viewDirection);
    float NdotH = max(0.0, dot(normalize(normal), halfVector));
    vec3 specular = vec3(pow(NdotH, 16.0))*spc;

    float zLight = texture(depthTexture, depthTextureCoord.xy).r;
    float zActual = depthTextureCoord.z;
    bool shadow = zActual > zLight + 0.001;

    float att=1.0/(constantAttenuation +
    linearAttenuation * dist +
    quadraticAttenuation * dist * dist);



    vec4 finalColor;

    switch (colorMode){
        case 0:
        finalColor = vec4(ambient+diffuse+specular, 1.0);
        break;
        case 1:
        finalColor = vec4(normalize(normal)+1, 1.0);
        break;
    }


    if (shadow) {
        outColor = vec4((ambient), 1.0)*finalColor;
    } else {

        outColor = vec4((ambient+att*(diffuse + specular)), 1.0)* finalColor;
    }
}
