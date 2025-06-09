#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <morecolorful:wind.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;
in vec4 Wave;

uniform sampler2D Sampler2;
uniform float GameTime;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;
uniform int FogShape;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;

#define pi 3.1415926535897932

void main() {
    vec3 position = Position + ChunkOffset;
    vec3 position1 = Position / 2.0 * pi;
    float animation = GameTime * 800.0;
    float speedX = unpack_x(Wave.x);
    float speedY = unpack_y(Wave.y);
    float speedZ = unpack_z(Wave.x);
    float windSpeed = length(vec2(speedX, speedZ));
    int type = unpack_type(Wave.y);
    float xs = 0.0;
    float ys = 0.0;
    float zs = 0.0;

    if (type == 4) {
        float tip = unpack_high(Wave.z);
        float height = unpack_low(Wave.z);
        vec3 offset = accumulate_offset_chain(animation, vec3(speedX, speedY, speedZ), windSpeed, tip, height, Wave.w);
        xs = offset.x;
        ys = offset.y;
        zs = offset.z;
    } else {
        xs = sin(position1.x + (position1.y / 2.0) + animation * speed_multiplier(speedX)) * speedX / 5.0 + speedX / 2.0;
        ys = cos(position1.y + (position1.y / 2.0) + animation * speed_multiplier(speedY)) * speedY / 5.0 + speedY / 2.0;
        zs = cos(position1.z + (position1.y / 2.0) + animation * speed_multiplier(speedZ)) * speedZ / 5.0 + speedZ / 2.0;
    }

    gl_Position = ProjMat * ModelViewMat * (vec4(position, 1.0) + vec4(xs / 32.0, ys / 24.0, zs / 32.0, 0.0));

    vertexDistance = fog_distance(position, FogShape);
    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}