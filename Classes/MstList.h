#pragma once

template <typename T = cocos2d::CCObject*>
class MstList : public cocos2d::CCObject
{
public:
	MstList() = default;
	virtual ~MstList() = default;

	void addObject(T* obj)
	{
		if (!obj)
			return;

		obj->autorelease();
		m_array.addObject(obj);
	}

	unsigned int getCount() const
	{
		m_array.count();
	}

	T* getObject(int x)
	{
		return m_array.getObjectAtIndex(x);
	}

	void removeObject(int x)
	{
		m_array.removeObjectAtIndex(x);
	}

	void removeAllObjects()
	{
		m_array.removeAllObjects(true);
	}

protected:
	cocos2d::CCMutableArray<T> m_array;
};
