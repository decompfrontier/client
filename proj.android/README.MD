# Client android build
Decompiled and fixed gradle project for Android (does not build the library in ndk!)

## Dependencies
- Android SDK 33
- Gradle

## Setting up firebase
1. Go to [firebase console](https://console.firebase.google.com)
2. Create a new project (or use an existing one)
3. Create a new Android package by clicking the android icon
4. Set the name to `Brave Frontier` and the package id to `sg.gumi.bravefrontier`
5. Download `google-services.json` and put it inside the `app` directory.
6. You can now build the game

## Notes
Some dependencies has been removed from the game due to being closed source SDKs with no access.

Dependencies might not be 100% the same / some code was upgraded and fixed to make it compile with the latest version of everything.

Dependencies removed:
- Appflyer
- Crashlytics
- Tapjoy
- Ironsource
- Vungle
- Soomla

Some functions are not correctly accurate, the reason of that is because
Krakatau doesn't support synced objects, see [this issue](https://github.com/Storyyeller/Krakatau/issues/152) for more information.
Such code has to be investigated and fixed properly (TODO: INACCURATE or TODO: MISSING)

The code is also missing polishing as we cannot decompile and resolve enumerators or similar data.
