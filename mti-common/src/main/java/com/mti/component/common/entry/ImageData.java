package com.mti.component.common.entry;

import java.io.Serializable;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by jignzhanwu
 * @date 2020/5/27
 * @change
 * @describe 图片实体
 **/
public class ImageData implements Serializable {
    //显示图片的地址
    private String url;
    //文件全名称，比如，视频时为视频的播放路径
    private String fileName;

    public ImageData() {

    }

    public ImageData(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
