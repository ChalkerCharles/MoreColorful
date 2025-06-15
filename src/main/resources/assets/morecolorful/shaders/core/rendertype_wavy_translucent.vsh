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
    vec3 pos = Position + ChunkOffset;
    vec3 position = Position / 2.0 * pi;
    float animation = GameTime * 800.0;
    float speedX = unpack_x(Wave.x);
    float speedY = unpack_y(Wave.y);
    float speedZ = unpack_z(Wave.x);
    int wave_level = int(Wave.z);
    int type = unpack_type(Wave.y);
    float xs = 0.0;
    float ys = 0.0;
    float zs = 0.0;

    if (type == 3 && wave_level < 3) {
        float m0 = distance(Position.xz, vec2(8.0, 8.0)) * 10.0;
        ys = cos(m0 + animation * speed_multiplier(length(vec2(speedX, speedZ)))) * 0.65;
        if (wave_level == 1) {
            xs = sin(position.x + animation * speed_multiplier(speedX)) * cos(GameTime * 300);
            zs = cos(position.z + animation * speed_multiplier(speedZ)) * sin(GameTime * 300);
        }
    }

    gl_Position = ProjMat * ModelViewMat * (vec4(pos, 1.0) + vec4(xs / 32.0, ys / 24.0, zs / 32.0, 0.0));

    vertexDistance = fog_distance(pos, FogShape);
    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
