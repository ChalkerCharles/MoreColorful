#version 330 core

#import <sodium:include/fog.glsl>
#import <sodium:include/chunk_matrices.glsl>
#import <sodium:include/chunk_material.glsl>
#import <morecolorful:include/sodium_vertex.glsl>
#import <morecolorful:include/wind.glsl>

out vec4 v_Color;
out vec2 v_TexCoord;

out float v_MaterialMipBias;
#ifdef USE_FRAGMENT_DISCARD
out float v_MaterialAlphaCutoff;
#endif

#ifdef USE_FOG
out float v_FragDistance;
#endif

uniform int u_FogShape;
uniform vec3 u_RegionOffset;
uniform vec2 u_TexCoordShrink;
uniform float u_GameTime;

uniform sampler2D u_LightTex; // The light map texture sampler

uvec3 _get_relative_chunk_coord(uint pos) {
    // Packing scheme is defined by LocalSectionIndex
    return uvec3(pos) >> uvec3(5u, 0u, 2u) & uvec3(7u, 3u, 7u);
}

vec3 _get_draw_translation(uint pos) {
    return _get_relative_chunk_coord(pos) * vec3(16.0);
}
#define pi 3.1415926535897932

void main() {
    _vert_init();

    vec3 pos = (_vert_position / 2.0) * pi;
    float animation = u_GameTime * 800.0;
    float speedX = unpack_x(_wave.x);
    float speedY = unpack_y(_wave.y);
    float speedZ = unpack_z(_wave.x);
    float windSpeed = length(vec2(speedX, speedZ));
    float tip = unpack_high(_wave.z);
    float height = unpack_low(_wave.z);
    vec3 offset = accumulate_offset(animation, speedX, speedZ, windSpeed, tip, height);
    int type = unpack_type(_wave.y);
    float xs = 0.0;
    float ys = 0.0;
    float zs = 0.0;

    switch (type) {
        case 0: // No Waving
            break;
        case 1: // Flowerbeds
            xs = sin(pos.x + pos.y + animation * speed_multiplier(speedX)) * speedX / 8.0 + speedX / 5.0;
            zs = cos(pos.z + pos.y + animation * speed_multiplier(speedZ)) * speedZ / 8.0 + speedZ / 5.0;
            break;
        case 2: // Vines
            xs = sin(pos.x + (pos.y / 2.0) + animation * speed_multiplier(speedX)) * speedX / 5.0 + offset.x;
            ys = offset.y;
            zs = cos(pos.z + (pos.y / 2.0) + animation * speed_multiplier(speedZ)) * speedZ / 5.0 + offset.z;
            break;
        case 3: // Waterlilies, Duckweeds and Water
            float m0 = distance(_vert_position.xz, vec2(8.0, 8.0)) * 10.0;
            if (height == 0.0) {
                xs = sin(pos.x + animation * speed_multiplier(speedX)) * cos(u_GameTime * 300);
                ys = cos(m0 + animation * speed_multiplier(windSpeed)) * 0.65;
                zs = cos(pos.z + animation * speed_multiplier(speedZ)) * sin(u_GameTime * 300);
            } else if (height < 3.0) {
                ys = cos(m0 + animation * speed_multiplier(length(vec2(speedX, speedZ)))) * 0.65;
                if (height == 1.0) {
                    xs = sin(pos.x + animation * speed_multiplier(speedX)) * cos(u_GameTime * 300);
                    zs = cos(pos.z + animation * speed_multiplier(speedZ)) * sin(u_GameTime * 300);
                }
            }
            break;
        case 4: // Chains and Lanterns
            vec3 offset_chain = accumulate_offset_chain(animation, vec3(speedX, speedY, speedZ), windSpeed, tip, height, _wave.w);
            xs = offset_chain.x;
            ys = offset_chain.y;
            zs = offset_chain.z;
            break;
        case 5: // Hanging Plants (Cave Vines, Weeping Vines...)
            xs = sin(pos.x + pos.y + animation * speed_multiplier(speedX)) * speedX / 5.0 + offset.x;
            ys = offset.y;
            zs = cos(pos.z + pos.y + animation * speed_multiplier(speedZ)) * speedZ / 5.0 + offset.z;
            break;
        case 6: // Leaves
            xs = sin(pos.x + (pos.y / 2.0) + animation * speed_multiplier(speedX)) * speedX / 5.0 + speedX / 2.0;
            ys = cos(pos.y + (pos.y / 2.0) + animation * speed_multiplier(speedY)) * speedY / 5.0 + speedY / 2.0;
            zs = cos(pos.z + (pos.y / 2.0) + animation * speed_multiplier(speedZ)) * speedZ / 5.0 + speedZ / 2.0;
            break;
        default: // Other Plants
            xs = sin(pos.x + pos.y + animation * speed_multiplier(speedX)) * speedX / 5.0 + offset.x;
            ys = -offset.y;
            zs = cos(pos.z + pos.y + animation * speed_multiplier(speedZ)) * speedZ / 5.0 + offset.z;
    }

    // Transform the chunk-local vertex position into world model space
    vec3 translation = u_RegionOffset + _get_draw_translation(_draw_id);
    vec3 position = _vert_position + translation;

#ifdef USE_FOG
    v_FragDistance = getFragDistance(u_FogShape, position);
#endif

    // Transform the vertex position into model-view-projection space
    gl_Position = u_ProjectionMatrix * u_ModelViewMatrix * (vec4(position, 1.0) + vec4(xs / 32.0, ys / 24.0, zs / 32.0, 0.0));

    // Add the light color to the vertex color, and pass the texture coordinates to the fragment shader
    v_Color = _vert_color * texture(u_LightTex, _vert_tex_light_coord);
    v_TexCoord = (_vert_tex_diffuse_coord_bias * u_TexCoordShrink) + _vert_tex_diffuse_coord; // FMA for precision

    v_MaterialMipBias = _material_mip_bias(_material_params);
#ifdef USE_FRAGMENT_DISCARD
    v_MaterialAlphaCutoff = _material_alpha_cutoff(_material_params);
#endif
}