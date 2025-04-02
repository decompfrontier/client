@echo off
rmdir /s/q replika\build\android-arm64-v8a 2>NUL
md replika\build\android-arm64-v8a
cd replika\build\android-arm64-v8a
cmake -DANDROID=1 -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebInfo -DTARGET_ARCH=arm64-v8a -DCMAKE_TOOLCHAIN_FILE=../../ndktoolchain.cmake ../../../
cd ..\..\..\
