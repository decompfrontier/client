file(GLOB SRCS "*.cpp" "*.hpp")

if (ANDROID)
    set(TARGET game)
    add_library(${TARGET} SHARED ${SRCS})
endif()

target_link_libraries(${TARGET} PRIVATE cocos2d-x)
