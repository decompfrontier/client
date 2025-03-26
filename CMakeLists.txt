# Main cmake bootstrap of Brave Frontier
cmake_minimum_required(VERSION 3.31)
project(BraveFrontier)

cmake_policy(VERSION 3.10)

set(BUILD_ID "d0c5b70a6833160c9ac3a346be68adbcfc4c8d06")

if (ANDROID)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} --build-id=${BUILD_ID}")
else()
    message(FATAL_ERROR "Unsupported platform")
endif()

add_subdirectory(src)
