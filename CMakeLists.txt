# Main cmake bootstrap of Brave Frontier
cmake_minimum_required(VERSION 3.30)
project(BraveFrontier)

set(CMAKE_CXX_STANDARD 98)
set(CMAKE_CXX_STANDARD_REQUIRED 98)

set(BUILD_ID "d0c5b70a6833160c9ac3a346be68adbcfc4c8d06")

if (ANDROID)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} --build-id=${BUILD_ID}")
    add_definitions(-DANDROID=1)

    if("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "aarch64")
        set(PROJECT_LIBS_ARCH "arm64-v8a")
    else()
        message(FATAL_ERROR "Unsupported Android arch ${CMAKE_SYSTEM_PROCESSOR}")
    endif()

    set(PROJECT_EXTERNAL_LIBS "${CMAKE_CURRENT_LIST_DIR}/libs/android/${PROJECT_LIBS_ARCH}")
else()
    message(FATAL_ERROR "Unsupported platform")
endif()


add_subdirectory(libs)
add_subdirectory(src)
