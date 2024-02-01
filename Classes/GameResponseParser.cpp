#include "pch.h"
#include "GameResponseParser.h"
#include "HeaderResponse.h"
#include "BodyResponse.h"
#include "MessageResponse.h"

void GameResponseParser::parseTag(const std::map<std::string, picojson::value>& json, const char* key, const char* aesKey)
{
	BaseResponse* resp = nullptr;

	if (!strcmp(key, GAMERESPONSE_HEADER))
	{
		resp = new HeaderResponse();
	}
	else if (!strcmp(key, GAMERESPONSE_BODY))
	{
		resp = new BodyResponse();
	}
	else if (!strcmp(key, GAMERESPONSE_MESSAGE))
	{
		resp = new MessageResponse();
	}

	resp->autorelease();

	auto it = json.find(key);

	if (it != json.end())
	{
		if (it->second.is<picojson::object>())
		{
			auto& obj = it->second.get<picojson::object>();

			bool isFirst = true;
			bool result = true;

			for (const auto& v : obj)
			{
				result = resp->readParam(0, 0, v.first.c_str(), v.second.to_str().c_str(), isFirst);
				isFirst = false; // DECOMP note: this is not 100% what bf does but I think it does this way

				if (!result)
					break;
			}

			if (result && (strcmp(key, GAMERESPONSE_HEADER) > 0 || !dynamic_cast<HeaderResponse*>(resp)->getIsError()))
			{
				if (!strcmp(key, GAMERESPONSE_BODY))
				{
                    auto q = dynamic_cast<BodyResponse*>(resp);
					auto decoded = CommonUtils::decodeCStringForBase64(q->getEncodeData().c_str(), aesKey);

					if (decoded != "[]")
					{
                        picojson::value out;
                        picojson::parse(out, decoded);

                        for (i = (const char*)v42[6]; ; i = (char*)v42 + 33)
                        {
                            if (!(unsigned __int8)GameResponseParser::parseBodyTag(v40, v41, i))
                            {
                                v50 = 1;
                                goto LABEL_90;
                            }
                            v40 = (SaveData*)i;
                            if (!strcmp(i, "KeC10fuL"))
                                v44 = 1;
                            v46 = (_QWORD*)v42[1];
                            if (v46)
                            {
                                do
                                {
                                    v47 = v46;
                                    v46 = (_QWORD*)*v46;
                                } while (v46);
                            }
                            else
                            {
                                v47 = (_QWORD*)v42[2];
                                if ((_QWORD*)*v47 != v42)
                                {
                                    v48 = (__int64)(v42 + 2);
                                    do
                                    {
                                        v49 = *(_QWORD*)v48;
                                        v47 = *(_QWORD**)(*(_QWORD*)v48 + 16LL);
                                        v48 = *(_QWORD*)v48 + 16LL;
                                    } while (*v47 != v49);
                                }
                            }
                            v42 = v47;
                            if (v47 == v43)
                                break;
                            if ((v47[4] & 1) != 0)
                                goto LABEL_73;
                        LABEL_72:
                            ;
                        }
                        v50 = 0;
                        if ((v44 & 1) != 0)
                        {
                            v51 = (SaveData*)SaveData::shared(v40);
                            SaveData::saveVersionInfo(v51);
                            v52 = (SaveData*)SaveData::shared(v51);
                            SaveData::saveFileCntInfo(v52);
                        }
                    }
				}
			}
		}
	}
}
