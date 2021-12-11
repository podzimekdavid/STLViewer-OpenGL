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
uniform vec3 reflector;
uniform int isReflector;

uniform int amb;
uniform int dif;
uniform int spc;

uniform float constantAttenuation, linearAttenuation, quadraticAttenuation;

out vec4 outColor;

void main() {
    /*vec3 ambient = vec3(0.1)*amb;

    float NdotL = max(0, dot(normalize(light), normalize(normal)));
    vec3 diffuse = vec3(NdotL * vec3(0.5))*dif;

    vec3 halfVector = normalize(light + viewDirection);
    float NdotH = max(0.0, dot(normalize(normal), halfVector));
    vec3 specular = vec3(pow(NdotH, 16.0))*spc;

    vec4 textureColor = texture(imageTexture, texCoord).rgba;

    float zLight = texture(depthTexture, depthTextureCoord.xy).r;
    float zActual = depthTextureCoord.z;
    bool shadow = zActual > zLight + 0.001;

    float att=1.0/(constantAttenuation +
    linearAttenuation * dist +
    quadraticAttenuation * dist * dist);



    vec4 finalColor;

    switch (colorMode){
        case 0:
        finalColor=textureColor;
        break;
        case 1:
        finalColor=vec4(positionA, 1.0);
        break;
        case 2:
        finalColor = vec4(depthTextureCoord.zzz, 1.0);
        break;
        case 3:
        finalColor = vec4(ambient+diffuse+specular, 1.0);
        break;
        case 4:
        finalColor = vec4(normalize(normal), 1.0);
        break;
    }


    if (isReflector==1){

        float spotCutOff=0.976;
        float spotEffect = max(dot(normalize(reflector), normalize(-normalize(light))), 0);
        if (!shadow&&spotEffect > spotCutOff) {

            float blend = clamp(
            (spotEffect-spotCutOff)/(1-spotCutOff)
            , 0.0, 1.0);
            vec3 mixColor = mix(ambient,
            ambient+att*(diffuse +
            specular), blend);

            outColor = vec4(mixColor, 1.0)* finalColor;
        } else {

            outColor = vec4((ambient), 1.0)*finalColor;
        }
    } else {

        if (shadow) {
            outColor = vec4((ambient), 1.0)*finalColor;
        } else {

            outColor = vec4((ambient+att*(diffuse + specular)), 1.0)* finalColor;
        }
    }
*/

    outColor = vec4(1,1,1, 1.0);
}
