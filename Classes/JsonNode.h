#pragma once

#include "JsonParam.h"

class JsonNode : public cocos2d::CCNode
{
public:
	JsonNode() = default;
	virtual ~JsonNode() = default;

	void addParam(const char* k, const char* v)
	{
		addParam(k, std::string(v));
	}

	void addParam(const char* k, int v)
	{
		addParam(k, CommonUtils::IntToString(v));
	}

	void addParam(const char* k, long long v)
	{
		addParam(k, CommonUtils::LongLongToString(v));
	}

	void addParam(const char* k, std::string v)
	{
		auto param = new JsonParam(k, v);
		m_array.addObject(param);
	}

	std::string getSendData(std::string& out)
	{
		out = "{";
		for (unsigned int i = 0; i < m_array.count(); i++)
		{
			const auto& obj = m_array.getObjectAtIndex(i);
			std::string param;
			out += obj->getSendData(param);

			if (i < (m_array.count() - 1))
				out += ",";
		}
		out += "}";
		return out;
	}

protected:
	cocos2d::CCMutableArray<JsonParam*> m_array;
};
