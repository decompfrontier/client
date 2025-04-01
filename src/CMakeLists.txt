set(SRCS
    AIMst.cpp
    AIMst.hpp
    Main_Android.cpp
)

if (ANDROID)
    set(TARGET game)
    add_library(${TARGET} SHARED ${SRCS})
    target_link_libraries(${TARGET} PRIVATE log GLESv2 m dl android OpenSLES)
endif()

target_link_libraries(${TARGET} PRIVATE cocos2d-x)
target_link_directories(${TARGET} PRIVATE ${PROJECT_EXTERNAL_LIBS})
