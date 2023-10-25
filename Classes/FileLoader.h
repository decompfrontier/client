#pragma once

class FileLoader : public cocos2d::CCObject
{
    SHARED_SINGLETON(FileLoader);

public:
    FileLoader();
    ~FileLoader();

    void addLoadFile(std::string, bool);
    void addLoadFile(std::string, std::string);
    void addLoadFilePriority(std::string, bool, bool);
    void cleanXMLFile();
    void clear();
    void count();
    void countPriority();
    void errorCountPriority();
    void finishCount();
    void finishCountPriority();
    void getConnectStartFlag();
    void getCutSceneMarkerCount();
    void getDLCompleteList();
    void getErrorMessage();
    void getErrorUrl();
    void getInitialDownloadWarningQuit();
    void getListOfErrorDownloads();
    void getListOfErrorDownloadsPriority();
    void getNode(int);
    void getRetryCnt();
    void getSamDLCMarkerCount();
    void handleIPH5Download(const std::string&);
    bool isConnect();
    bool isError();
    bool isErrorPriority();
    bool isExistInList(std::string);
    bool isFileAlreadyDownloaded(const std::string&);
    bool isFinished(int);
    bool isFinished();
    bool isFinishedPriority();
    bool isImage(int);
    void priorityDownloadExist();
    void processSamAndCutSceneFile();
    void purgeCompletedPriorityList();
    void purgeCompletedPriorityListOnSkip();
    void removeCompletedParsedFile(const std::string&);
    void retry();
    void retryPriority();
    void setConnectStartFlg(bool);
    void setInitialDownloadWarningQuit(bool);
    void setParsedFileMap();
    void specialCutSceneScriptHandler(std::string&);
    void specialHandleSamDLCMarkerIPH5();
    void specialSamHandler(std::string&);
    void start();
    void threadSleep(int);
    void update();
    void updatePriority(bool);


};
