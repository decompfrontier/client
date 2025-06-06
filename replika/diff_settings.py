def apply(config, args):
    import os
    import platform

    ## TODO: Add support for "--version" argument

    config["baseimg"] = "romset/android/global/arm64-v8a/libgame.so"
    config["mapfile"] = "romset/android/global/arm64-v8a/libgame.map"
    config["myimg"] = "build/android-arm64/src/libgame.so"
    config["source_directories"] = ["../src"]
    config["arch"] = "aarch64"
    config["map_format"] = "gnu" # gnu, mw, ms
    config["make_command"] = ["../make.bash" ]
    #config["build_dir"] = "replika/build/src" # only needed for mw and ms map format
    #config["objdump_flags"] = ["-m", "aarch64"]
    # config["expected_dir"] = "expected/" # needed for -o
    # config["makeflags"] = []

    # ill-suited to C++ projects (and too slow for large executables)
    config["show_line_numbers_default"] = False

    ext = ""
    defplat = "linux-x86_64"
    path = os.environ["ANDROID_SDK"]

    if path[-1] == "/" or path[-1] == "\\":
        path = path[:-1]

    if platform.system() == "Darwin":
        defplat = "darwin-x86_64"
    elif platform.system() == "Windows":
        defplat = "windows-x86_64"
        ext = ".exe"

    # android ndk r21
    #config["objdump_executable"] = "".join((path, "/ndk/21.0.6113669/toolchains/llvm/prebuilt/", defplat, "/bin/aarch64-linux-android-objdump", ext))
    config["objdump_executable"] = "aarch64-linux-gnu-objdump"
