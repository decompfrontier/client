#pragma once

class JsonParam : public cocos2d::CCNode
{
public:
	JsonParam(std::string key, std::string value) : m_key(key), m_value(value) {}
	virtual ~JsonParam() = default;

	std::string getSendData(std::string& out)
	{
		out = "\"";
		out += m_key;
		out += "\":\"";
		out += m_value;
		return out;
	}

protected:
	std::string m_key, m_value;
};
