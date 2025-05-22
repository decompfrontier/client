set(SRCS
    AIMst.cpp
    AIMst.hpp
    AsyncFileLoad.cpp
    AsyncFileLoad.hpp
    BaseScene.cpp
    BaseScene.hpp
    CommonUtils.cpp
    CommonUtils.hpp
    GameLayer.cpp
    GameLayer.hpp
    GameResponseParser.cpp
    GameResponseParser.hpp
    GameSprite.cpp
    GameSprite.hpp
    Main_Android.cpp
    NativeCallbackHandler.cpp
    NativeCallbackHandler.hpp
    NodeStatus.hpp
    Pch.hpp
    PTRateThisAppPopup.hpp
    PTRateThisAppPopup.cpp
	PWFacebook.cpp
	PWFacebook.hpp
    PWFacebookAndroid.cpp
    PWFacebookAndroid.hpp
    PWFacebookUser.hpp
    RegionSwitch.cpp
    RegionSwitch.hpp
    RequestData.hpp
    ScrlLayer.cpp
    ScrlLayer.hpp
    ScrollBar.hpp
    Tapjoy.cpp
    Tapjoy.hpp
	Utils.hpp
	Utils.cpp
    WebViewScene.cpp
    WebViewScene.hpp
    WrapAsyncFileLoad.cpp
    WrapAsyncFileLoad.hpp
)

if (ANDROID)
    set(TARGET game)
    set(LIB_PREFIX lib)
    add_library(${TARGET} SHARED ${SRCS})
    target_link_libraries(${TARGET} PRIVATE log GLESv2 m dl android OpenSLES)
elseif(WINDOWS)
    set(TARGET BraveFrontier.${WIN_NAME})
    add_executable(${TARGET} WIN32 ${SRCS})
endif()

target_link_libraries(${TARGET} PRIVATE cocos2d-x picojson)
target_link_directories(${TARGET} PRIVATE ${PROJECT_EXTERNAL_LIBS})
