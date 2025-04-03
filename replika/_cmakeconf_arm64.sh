#!/bin/sh
BUILD=replika/build/android-arm64-v8a/
rm -fr $BUILD
mkdir -p $BUILD
cmake -DANDROID=1 -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebInfo -DTARGET_ARCH=arm64-v8a -DCMAKE_TOOLCHAIN_FILE=replika/ndktoolchain.cmake -S ./ -B $BUILD
