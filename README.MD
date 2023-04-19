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
At the moment it only works for Windows (VS2022), while preliminary code for Android is being made. No support for iOS at this time.
No cmake support, I wish that once something like this is done we can switch to Cocos v4 and cmake :)

### Building
Open "Project.win32/BraveFrontier.sln"

## Legal
The source code of DecompFrontier is for **non-commercial use only**. If you use this code or create a derivate work you might not
implement or impose a charge for any possible thing (like Microtransaction or access).

Brave Frontier - (C) Alim Co., Ltd. All Rights Reserved.

This project is not associated, affiliated or sponsed with/by Alim Co., Ltd or gumi Inc.
