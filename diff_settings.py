def apply(config, args):
    import os
    import platform

    config["baseimg"] = "replika/romset/global/libgame-x86_64.so"
    config["mapfile"] = "replika/romset/global/libgame-x86_64.map"
    config["myimg"] = "replika/build/src/libgame.so"
    config["source_directories"] = ["."]
    # config["show_line_numbers_default"] = True
    config["arch"] = "x86_64"
    config["map_format"] = "gnu" # gnu, mw, ms
    # config["build_dir"] = "build/" # only needed for mw and ms map format
    # config["expected_dir"] = "expected/" # needed for -o
    # config["makeflags"] = []

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
    config["objdump_executable"] = "".join((path, "/ndk/21.0.6113669/toolchains/llvm/prebuilt/", defplat, "/bin/aarch64-linux-android-objdump", ext))
