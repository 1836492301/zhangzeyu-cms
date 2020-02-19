package com.zhangzeyu.cms.domain;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
public class ArticleWithBLOBs extends Article implements Serializable {
	private static final long serialVersionUID = 3784014497633443444L;
	@Field(index=true,analyzer="ik_smart",store=true,searchAnalyzer="ik_smart",type = FieldType.text)
	private String content;

    private String summary;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

	@Override
	public String toString() {
		return "ArticleWithBLOBs [content=" + content + ", summary=" + summary + "]标题:"+getTitle();
	}
    
}