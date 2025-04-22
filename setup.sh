#!/bin/bash
set -e

build_viking()
{
    if [[ -f "$PWD/replika/viking/target/debug/check" ]]; then
        return 0
    fi

    echo Building viking...

    cd "$PWD/replika/viking"
    cargo b -q

    if ! [ -f "target/debug/check" ]; then
        echo "Viking building failed"
        return 1
    fi

    cd "../.."
    return 0
}

install_ndk_r21e()
{
    if [[ -z "${ANDROID_SDK}" ]]; then
        export ANDROID_SDK=~/android-sdk
        echo "Installing Android SDK to $ANDROID_SDK..."
    fi

    if [[ -d "$ANDROID_SDK/ndk/21.0.6113669/toolchains" ]]; then
        return 0
    fi

    mkdir -p "$ANDROID_SDK"
    cd "$ANDROID_SDK"
    
    if ! [[ -f "android-ndk-r21e-linux-x86_64.zip" ]]; then
        wget https://dl.google.com/android/repository/android-ndk-r21e-linux-x86_64.zip
    fi

    7z x -bb 0 -aou -y android-ndk-r21e-linux-x86_64.zip
    mkdir -p ndk/21.0.6113669
    cp -Rf android-ndk-r21e/* ndk/21.0.6113669/
    rm -fr android-ndk-r21e android-ndk-r21e-linux-x86_64.zip
    cd "$PWD"
    return 0
}

build_viking
install_ndk_r21e
./replika/viking/target/debug/check
