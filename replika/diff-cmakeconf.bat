@echo off
rmdir /s/q replika\build
md replika\build
cd replika\build
cmake -DANDROID=1 -G Ninja -DCMAKE_TOOLCHAIN_FILE=../ndktoolchain.cmake ../../
cd ..\..\
