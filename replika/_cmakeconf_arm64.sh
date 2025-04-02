#!/bin/sh
rm -fr replika/build/android-arm64-v8a
mkdir -p replika/build/android-arm64-v8a
cd replika/build/android-arm64-v8a
cmake -DANDROID=1 -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebInfo -DTARGET_ARCH=arm64-v8a -DCMAKE_TOOLCHAIN_FILE=../../ndktoolchain.cmake ../../../
cd ../../../
