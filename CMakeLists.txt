# Main cmake bootstrap of Brave Frontier
cmake_minimum_required(VERSION 3.30)
project(BraveFrontier)

set(CMAKE_CXX_STANDARD 98)
set(CMAKE_CXX_STANDARD_REQUIRED 98)

if (ANDROID)
    add_definitions(-DANDROID=1)

    if("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "aarch64" OR "${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "ARM64")
        set(PROJECT_LIBS_ARCH "arm64-v8a")
        set(BUILD_ID "d0c5b70a6833160c9ac3a346be68adbcfc4c8d06")
    elseif("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "AMD64" OR "${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "x86_64")
        set(PROJECT_LIBS_ARCH "x86_64")
        set(BUILD_ID "ed9a8e52c001fab46cabb8854c37ea42d85f34d9")
    elseif("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "X86" OR "${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "i386")
        set(PROJECT_LIBS_ARCH "x86")
        set(BUILD_ID "ed9a8e52c001fab46cabb8854c37ea42d85f34d9")
    elseif("${CMAKE_SYSTEM_PROCESSOR}" STREQUAL "arm")
        set(PROJECT_LIBS_ARCH "armeabi-v7a")
        set(BUILD_ID "7a9ec1b759e8ad95aa5f06ceefd7a87cc1629d77")
    else()
        message(FATAL_ERROR "Unsupported Android arch ${CMAKE_SYSTEM_PROCESSOR}")
    endif()

    set(PROJECT_EXTERNAL_LIBS "${CMAKE_CURRENT_LIST_DIR}/libs/android/${PROJECT_LIBS_ARCH}")
else()
    message(FATAL_ERROR "Unsupported platform")
endif()

add_subdirectory(libs)
add_subdirectory(src)
