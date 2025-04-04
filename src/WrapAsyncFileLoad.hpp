#pragma once

class WrapAsyncFileLoad
{
public:
    void connectionDidFailWithError(const char *error);
    void connectionDidFinishLoading(jsize length, const jbyte *elements);
};
