@echo off
rmdir /s/q replika\build
md replika\build
cd replika\build
cmake -DANDROID=1 -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebInfo -DCMAKE_TOOLCHAIN_FILE=../ndktoolchain.cmake ../../
cd ..\..\
