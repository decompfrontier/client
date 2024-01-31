#pragma once

#include "JsonNode.h"

class JsonGroup : public cocos2d::CCNode
{
public:
	JsonGroup(const char* groupName) : m_groupName(groupName) {}
	virtual ~JsonGroup() = default;

	std::string getSendData(std::string out)
	{
		out = "\"";
		out += m_groupName;
		out += "\":[";

		for (unsigned int i = 0; i < m_nodes.count(); i++)
		{
			const auto& node = m_nodes.getObjectAtIndex(i);
			std::string nodeData;
			out += node->getSendData(nodeData);

			if (i < (m_nodes.count() - 1))
				out += ",";
		}

		out += "]";

		return out;
	}


protected:
	const char* m_groupName;
	cocos2d::CCMutableArray<JsonNode*> m_nodes;
};
