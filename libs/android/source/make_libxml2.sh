#!/bin/sh
NDK_ROOT=$ANDROID_SDK/ndk/21.0.6113669/
API_LEVEL=21
SYSROOT=$NDK_ROOT/platforms/android-$API_LEVEL/arch-arm64

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
OSNAME=linux-x86_64
elif [[ "$OSTYPE" == "darwin"* ]]; then
OSNAME=darwin-x86_64
else
OSNAME=windows-x86_64
fi

export CC="$NDK_ROOT/toolchains/llvm/prebuilt/$OSNAME/bin/aarch64-linux-android$API_LEVEL-clang"
export CFLAGS="--sysroot=$SYSROOT"
export LDFLAGS="--sysroot=$SYSROOT"

cd libxml2
./autogen.sh --host=aarch64-linux-gnueabi --enable-static --disable-shared
make libxml2.la $*
cd ..
