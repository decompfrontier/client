# Client
Decompilation of Brave Frontier v2.19.6.0 (ARM64 Android)

## What is this?
The following is a **WIP**, **EXPERIMENTAL** and probably **NEVER COMPLETED** decompilation of the original, and last known version, of Brave Frontier (v2.19.6.0) from the Android ARM64 version.

The C++ code was decompiled with [IDA Pro (64-bit)](https://hex-rays.com/), you can import the current IDC file from [here](https://github.com/decompfrontier/idaidc).
The Java code was decompiled and fixed with [Krakatau](https://github.com/Storyyeller/Krakatau) and dependencies from the linked Android SDK.

Any special code for the extended functionlities (like a possible Offline Mod or PC edition) will be marked with `__DECOMP__`.

## Why this and not a recreation?
I always wanted to start a decompilation of some games, Brave Frontier left us debugging symbols that we can alayize and have fun,
and I originally did some experiments back in 2019 and 2022 which lead me to attempt my first decompilation with Brave.

I feel like I want to preserve Brave in the original way it was written (using cocos2d-x and all the other trashies), which is why
I belive a recreation would feel out of scope for this project (even if it will be completed in a lesser time and will provide
better features).

If you look for a recreation or something that has a proper goal, take a 
look at [Brave Frontier: ReCoded](https://twitter.com/BFRecoded), a project with fairly better scope than this decomp.

## Compiling

### Windows
1. Install Visual Studio 2022
2. Open "Project.win32/BraveFrontier.sln"

### Android
**NOTE:** Building of libgame.so (actual brave frontier) is currently NOT supported!
1. Install Android Studio (or IntelliJ IDEA)
2. Open "Proj.android" directory

### Other platforms
This decompilation currently does **NOT** support any other platform than Android and Windows, if the project eventually gets finished
I would like to enroll with cocos2d-x 4.0, cmake fixes to avoid as much JNI as possible so PC platforms (Windows, Linux and MacOS) would work. Once that is actually done, eventually iOS support can be remade entirely (or decompiled providing someone does know how...).

## Legal
The source code of DecompFrontier is for **non-commercial use only**. If you use this code or create a derivate work you might not
implement or impose a charge for any possible thing (like Microtransaction or access).

Brave Frontier - (C) Alim Co., Ltd. All Rights Reserved.

This project is not associated, affiliated or sponsored with/by Alim Co., Ltd or gumi Inc.
