#!/bin/bash
if ! [[ $ANDROID_SDK ]]; then
	echo Please setup the root path of the Android SDK
	exit 1
fi

rebuild=false
arch=


while getopts ":rv" option; do
	case $option in
		r) rebuild=true ;;
		v) arch=$OPTARG ;;
	esac
done

if ! [[ $arch ]]; then
	arch=arm64
fi

build_name=
target_ndk=
current_dir=$PWD

case $arch in
	arm64)
		build_name=android-arm64
		target_ndk=arm64-v8a
	;;
	arm32)
		build_name=android-arm32
		target_ndk=armeabi-v7a
	;;
	x86)
		build_name=android-x86
		target_ndk=x86
	;;
	x64)
		build_name=android-x64
		target_ndk=x86_64
	;;
	*)
		echo "The architecture $arch is invalid"
		exit 1
	;;
esac

build_dir=$current_dir/replika/build/$build_name

if $rebuild; then
	rm -fr $build_dir
fi

if ! [[ -d $build_dir ]]; then
	mkdir -p $build_dir
	cmake -DANDROID=1 -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebInfo -DTARGET_ARCH=$target_ndk -DCMAKE_TOOLCHAIN_FILE="$current_dir/replika/ndktoolchain.cmake" -S "$current_dir" -B "$build_dir"
fi

ninja -C "$build_dir"
