# Main cmake bootstrap of Brave Frontier
cmake_minimum_required(VERSION 3.30)
project(BraveFrontier)

set(BUILD_ID "d0c5b70a6833160c9ac3a346be68adbcfc4c8d06")

if (ANDROID)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} --build-id=${BUILD_ID}")
    add_definitions(-DANDROID=1)
else()
    message(FATAL_ERROR "Unsupported platform")
endif()

add_subdirectory(libs)
add_subdirectory(src)
