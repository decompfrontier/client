set(SRCS
    AIMst.cpp
    AIMst.hpp
    AsyncFileLoad.cpp
    AsyncFileLoad.hpp
    CommonUtils.cpp
    CommonUtils.hpp
    GameResponseParser.cpp
    GameResponseParser.hpp
    Main_Android.cpp
    NativeCallbackHandler.cpp
    NativeCallbackHandler.hpp
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
    Tapjoy.cpp
    Tapjoy.hpp
	Utils.hpp
	Utils.cpp
    WrapAsyncFileLoad.cpp
    WrapAsyncFileLoad.hpp
)

if (ANDROID)
    set(TARGET game)
    set(LIB_PREFIX lib)
    add_library(${TARGET} SHARED ${SRCS})
    target_link_libraries(${TARGET} PRIVATE log GLESv2 m dl android OpenSLES)
endif()

target_link_libraries(${TARGET} PRIVATE cocos2d-x picojson)
target_link_directories(${TARGET} PRIVATE ${PROJECT_EXTERNAL_LIBS})
