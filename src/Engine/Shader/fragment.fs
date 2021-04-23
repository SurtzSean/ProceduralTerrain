#version 330

in vec2 outTexCord;
out vec4 outTex;

uniform sampler2D tex;
void main()
{
    outTex = texture(tex, outTexCord);
}