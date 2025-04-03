# Brave Frontier

This project contains an experimental, **work-in-process** decompilation of Brave Frontier Client v.2.9.16.0 for Android x86_64 (64-bit), it might eventually be extended to
more platforms.

The project might not be buildable at the moment, for more information about what and the reason why this project was made,
please see the page [Client decompilation architecture]() and [Client decompilation status](https://decompfrontier.github.io/pages/Status/decomp-status.html) for 
the current progress of the project.

## Building
Currently only building the Android version can work, you should build it by opening `src/android` with Android Studio/IntelliJ and try to build.

### Setting up firebase
1. Go to [firebase console](https://console.firebase.google.com)
2. Create a new project (or use an existing one)
3. Create a new Android package by clicking the android icon
4. Set the name to `Brave Frontier` and the package id to `sg.gumi.bravefrontier`
5. Download `google-services.json` and put it inside the `src/android/app` directory.
6. You can now build the game

## Matching
Currently the infrastructure is not ready for matching, please keep an eye on the repo when this part is ready.

## Legal
As this project is made for the players by the players, I think this project should be used for **non-commercial use only**. 
If you use this code or create a derivate work you might not implement or impose a charge for any possible thing (like Microtransactions or ads).

Brave Frontier - (C) Alim Co., Ltd. All Rights Reserved.

This project is not associated, affiliated or sponsored with/by Alim Co., Ltd or gumi Inc.

## Notes
Special thanks to the [Zelda Breath of the Wild](https://github.com/zeldaret/botw) decompilation project as many pieces of this
infrastructure were based upon them.
