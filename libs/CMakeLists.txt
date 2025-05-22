# cocos2d-x 2.0.3

set(COCOS2DX_ROOT "${CMAKE_CURRENT_LIST_DIR}/cocos2d-x")
set(COCOS_SOURCES )
set(COCOS_PUBLIC_INCS )
set(COCOS_FILES 
    actions
    base_nodes
    cocoa
    effects
    include
    kazmath
    keypad_dispatcher
    label_nodes
    layers_scenes_transitions_nodes
    menu_nodes
    misc_nodes
    particle_nodes
    platform
    script_support
    shaders
    sprite_nodes
    support
    text_input_node
    textures
    tilemap_parallax_nodes
    touch_dispatcher
    .
)

if (ANDROID)
    list(APPEND COCOS_FILES "platform/android")
    list(APPEND COCOS_FILES "platform/android/jni")
    list(APPEND COCOS_PUBLIC_INCS "${COCOS2DX_ROOT}/platform/cocos2dx/android/jni")
    set(COCOS_PLATFORM_INC "platform/android")
    set(COCOS_LIB_TYPE STATIC)
elseif (WINDOWS)
    list(APPEND COCOS_FILES "platform/win32")
    set(COCOS_PLATFORM_INC "platform/win32")
    list(APPEND COCOS_PUBLIC_INCS "${COCOS2DX_ROOT}/cocos2dx/platform/win32")
    set(COCOS_LIB_TYPE SHARED)
endif()

foreach (DIR IN LISTS COCOS_FILES)
    set(COCOS_TARGET_TMP "${COCOS2DX_ROOT}/cocos2dx/${DIR}")
    file(GLOB COCOS_SRCS_TMP "${COCOS_TARGET_TMP}/*.cpp" "${COCOS_TARGET_TMP}/*.h")
    list(APPEND COCOS_SOURCES "${COCOS_SRCS_TMP}")
    list(APPEND COCOS_PUBLIC_INCS "${COCOS_TARGET_TMP}")
endforeach()

add_library(cocos2d-x ${COCOS_LIB_TYPE} ${COCOS_SOURCES})
target_include_directories(cocos2d-x
    PRIVATE 
        ${COCOS2DX_ROOT}/cocos2dx
        ${COCOS2DX_ROOT}/cocos2dx/include
        ${COCOS2DX_ROOT}/cocos2dx/platform
        ${COCOS2DX_ROOT}/cocos2dx/${COCOS_PLATFORM_INC}
    INTERFACE 
        ${COCOS_PUBLIC_INCS}
    PUBLIC
        ${COCOS2DX_ROOT}/cocos2dx/kazmath/include
        ${PROJECT_EXTERNAL_LIBS}
)
target_link_directories(cocos2d-x PRIVATE ${PROJECT_EXTERNAL_LIBS})
target_link_libraries(cocos2d-x PRIVATE xml2 tiff curl crypto ssl jpeg png16 z)

# picojson 1.1.0
add_library(picojson INTERFACE picojson/picojson.h)
target_include_directories(picojson INTERFACE ${CMAKE_CURRENT_LIST_DIR}/picojson)
