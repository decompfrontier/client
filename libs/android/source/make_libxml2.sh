#!/bin/sh
ANDROID_SDK_2=$(cygpath -u "$ANDROID_SDK")
NDK_ROOT=$ANDROID_SDK_2/ndk/21.0.6113669
API_LEVEL=21
SYSROOT=$NDK_ROOT/sysroot

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
OSNAME=linux-x86_64
elif [[ "$OSTYPE" == "darwin"* ]]; then
OSNAME=darwin-x86_64
else
OSNAME=windows-x86_64
fi

export CC="$NDK_ROOT/toolchains/llvm/prebuilt/$OSNAME/bin/aarch64-linux-android$API_LEVEL-clang"

rm -fr libxml2
mkdir libxml2
tar -xzf libxml2-a03fa1623ff0721f5e99fbaa26cfaa8c1388868a.tar.gz -C libxml2

cd libxml2
./autogen.sh --host=aarch64-linux-gnueabi --enable-static --disable-shared --without-iconv --without-lzma
