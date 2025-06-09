#version 150

float speed_multiplier(float speed) {
    return floor(speed + 0.5);
}

float unpack_high(float packedValue) {
    return float(int(packedValue) >> 16);
}

float unpack_low(float packedValue) {
    int temp = int(packedValue) & 0xFFFF;
    return temp > 32767 ? float(temp - 65536) : float(temp);
}

float unpack_posx(float packedValue) {
    return float(int(packedValue) >> 20) / 512.0;
}

float unpack_posy(float packedValue) {
    int temp = int(packedValue) & 0xFFFFF;
    return float(temp >> 10) / 512.0;
}

float unpack_posz(float packedValue) {
    return float(int(packedValue) & 1023) / 512.0;
}

int unpack_type(float packedValue) {
    return int(packedValue) >> 16;
}

float unpack_x(float packedValue) {
    return unpack_high(packedValue) / 1024.0;
}

float unpack_y(float packedValue) {
    return unpack_low(packedValue) / 1024.0;
}

float unpack_z(float packedValue) {
    return unpack_low(packedValue) / 1024.0;
}

float get_angle(float tick, float speed, float windSpeed) {
    float f = sin(tick * speed_multiplier(windSpeed) * 1.5) / 2.0 + 0.5;
    float angle = speed / 24.0;
    return mix(angle * 0.75, angle * 1.25, f);
}

vec3 accumulate_offset(float tick, float speedX, float speedZ, float windSpeed, float tip, float height) {
    float sum = tip + height + 1.0;
    float ratio, angleX, angleZ;
    vec3 offset = vec3(0.0);
    for (float i = 0.0; i < height; i++) {
        ratio = (i + 1.0) / sum;
        angleX = get_angle(tick, speedX, windSpeed) * ratio * ratio;
        angleZ = get_angle(tick, speedZ, windSpeed) * ratio * ratio;
        offset += vec3(sin(angleX), 1.0 - cos(length(vec2(angleX, angleZ))), sin(angleZ));
    }
    return offset * 24.0;
}

vec3 rotate_axis(float angle, float aX, float aY, float aZ, vec3 dest) {
    float hangle = angle * 0.5f;
    float sinAngle = sin(hangle);
    float qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
    float qw = cos(hangle);
    float w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
    float xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
    float x = dest.x, y = dest.y, z = dest.z;
    dest.x = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
    dest.y = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
    dest.z = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
    return dest;
}

vec2 rotate_90(vec2 vector) {
    return vec2(-vector.y, vector.x);
}

vec3 accumulate_offset_chain(float tick, vec3 wind, float windSpeed, float tip, float height, float packedValue) {
    float sum = tip + height + 1.0;
    float ratio, angleX, angleZ, angle, px, h, pz, dx, dy, dz;
    vec3 offset = vec3(0.0);
    for (float i = 0.0; i < height; i++) {
        ratio = (i + 1.0) / sum;
        angleX = get_angle(tick, wind.x, windSpeed) * ratio * ratio;
        angleZ = get_angle(tick, wind.z, windSpeed) * ratio * ratio;
        offset += vec3(sin(angleX), 1.0 - cos(length(vec2(angleX, angleZ))), sin(angleZ));
    }
    ratio = (height + 1.0) / sum;
    angle = get_angle(tick, windSpeed, windSpeed) * ratio * ratio;
    vec2 axis = rotate_90(normalize(wind.xz));
    px = unpack_posx(packedValue) - 0.5;
    h = unpack_posy(packedValue) - 1.0;
    pz = unpack_posz(packedValue) - 0.5;
    vec3 pos = vec3(px, h, pz);
    offset += rotate_axis(angle, axis.x, 0, axis.y, pos) - pos;
    return offset * 24.0;
}