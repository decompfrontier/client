#pragma once

#define REGION_SINGAPORE 0 // Global (sg.gumi.bravefrontier)
#define REGION_JAPAN 1 // Japense (jp.co.alim.bravefrontier)

#ifndef REGION
#define REGION REGION_SINGAPORE
#endif

static ALWAYS_INLINE std::string getBraveFrontierJNI()
{
    std::string path = "sg/gumi/bravefrontier/";
    std::string name = "BraveFrontierJNI";
    return path + name;
}
