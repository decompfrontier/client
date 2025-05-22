@ECHO OFF
cmake -DWINDOWS=1 -G "Visual Studio 17 2022" -A x64 -S . -B "replika/build/windows-x86_64"
