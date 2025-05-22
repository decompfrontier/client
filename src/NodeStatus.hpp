#pragma once

class NodeStatus : public cocos2d::CCObject
{
public:
    NodeStatus() : m_node(NULL), m_opacity(0), m_layerID(0) {}

    CC_SYNTHESIZE(cocos2d::CCNode*, m_node, Node);
    CC_SYNTHESIZE(int, m_opacity, Opacity);
    CC_SYNTHESIZE(int, m_layerID, LayerID);
};
