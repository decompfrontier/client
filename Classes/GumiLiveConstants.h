#pragma once

// dict keys
constexpr const auto APPLE_USER_ID_KEY = "AppleUserIDKey";
constexpr const auto APPLE_JWT_SECRET = "AppleJwtSecret";
constexpr const auto GUMI_LIVE_USERNAME_KEY = "GumiLiveUserNameKey";
constexpr const auto GUMI_LIVE_PASSWORD_KEY = "GumiLivePasswordKey";
constexpr const auto PT_SERVER_URL = "pt_server_url";
constexpr const auto GAME_USER_ID_KEY = "game_user_id";
constexpr const auto TOKEN_KEY = "token";

// json keys
constexpr const auto GUMI_PAYMENT_TOPUP_AMOUNT = "topup_amount";
constexpr const auto GUMI_PAYMENT_STATUS = "status";
constexpr const auto GUMI_PAYMENT_STATUS_NO = "status_no";

// Notifications
constexpr const auto CHECK_FOR_GUMI_LIVE_GUEST_ACCOUNT = "CheckForGumiLiveGuestAccount";
constexpr const auto GUMI_LIVE_LOGIN_SUCCESSFUL = "GumiLiveLoginSuccessful";
constexpr const auto GUMI_LIVE_TOKEN_REQUEST_GUEST_USER = "GumiLiveTokenRequestGuestUser";
constexpr const auto GUMI_LIVE_TOKEN_REQUEST_APPLE_EXIST_DATA = "GumiLiveTokenRequestAppleExistData";
constexpr const auto GUMI_LIVE_FORGOT_PASSWORD = "GumiLiveForgotPassword";
constexpr const auto GUMI_LIVE_BIND_CHECK = "GumiLiveBindCheck";
constexpr const auto GUMI_LIVE_MANAGER_LOGIN = "GumiLiveManagerLogin";
constexpr const auto GUMI_LIVE_BIND_CHECK = "GumiLiveBindCheck";
constexpr const auto GUMI_LIVE_UI_INPUT_TEXT_VISIBLE = "GumiLiveUIInputTextVisible";
constexpr const auto GUMI_LIVE_TOKEN_REQUEST_NEW_USER = "GumiLiveTokenRequestNewUser";
constexpr const auto GUMI_LIVE_TOKEN_REQUEST_APPLE = "GumiLiveTokenRequestApple";
constexpr const auto GUMI_LIVE_TOKEN_REQUEST_EXIST_DATA = "GumiLiveTokenRequestExistData";
constexpr const auto GUMI_LIVE_REQUEST_PASSWORD_RESET = "GumiLiveRequestPasswordReset";
constexpr const auto GUMI_LIVE_REQUEST_FACEBOOK_FRIEND_DATA = "GumiLiveRequestFacebookFriendData";
constexpr const auto GUMI_LIVE_FRIEND_DATA = "GumiLiveFriendData";

// php parameters
constexpr const auto GUMI_APPKEY = "ak";
constexpr const auto GUMI_DEVICEOS = "v";
constexpr const auto GUMI_DEVICEVID = "vid";
constexpr const auto GUMI_DEVICEMODEL = "dn";
constexpr const auto GUMI_DEVICEPLATFORM = "dp";
constexpr const auto GUMI_DEVICEALTVID = "altvid";
constexpr const auto GUMI_UNIQUEIDENTIFIERS = "identifiers";
constexpr const auto GUMI_DEVICEID = "device_id";
constexpr const auto GUMI_USERNAME = "username";
constexpr const auto GUMI_PASSWORD = "password";
constexpr const auto GUMI_DEVICEADID = "daid";
constexpr const auto GUMI_APPLE_ACCESS_IN = "apple_access_in";
constexpr const auto GUMI_USER_ID_KEY = "gumi_user_id";
constexpr const auto GUMI_SESSION_TO = "gumi_session_to";
constexpr const auto GUMI_IDS = "ids";
constexpr const auto GUMI_LIVEID = "GumiLiveID";
constexpr const auto GUMI_RECEIPT = "receipt";
constexpr const auto GUMI_CLIENTID = "client_id";

// API urls
constexpr const auto GUMI_LIVE_API_URLPATH_V1 = "/api/1.0/";
constexpr const auto GUMI_LIVE_API_URLPATH_V2 = "/api/1.1/";

// accounts urls
constexpr const auto GUMI_LIVE_PATH_GUEST_GET = "accounts/guest/get/";
constexpr const auto GUMI_LIVE_PATH_GUEST_LOGIN = "accounts/guest/login/";
constexpr const auto GUMI_LIVE_PATH_ACCOUNT_SIGNUP = "accounts/signup";
constexpr const auto GUMI_LIVE_PATH_GENERATE_RESET_PASSWORD = "accounts/generate_reset_password_link/";

// wallet urls
constexpr const auto GUMI_LIVE_PATH_WALLET_SAMSUNG = "wallet/samsung/verify";

// login urls
constexpr const auto GUMI_LIVE_PATH_LOGIN = "/login/";

// forward urls
constexpr const auto GUMI_LIVE_PATH_FACEBOOK_ACTION = "/bf/gme/action_fb.php";
constexpr const auto GUMI_LIVE_PATH_APPLE_ACTION = "/bf/gme/action_apple.php";
constexpr const auto GUMI_LIVE_PATH_FACEBOOK_FRIENDLIST = "/bf/gme/action_fblist.php";
