package pojo;

import java.io.Serializable;
import java.util.Date;

public class NewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // 完美匹配第一版 DDL 的 4 个核心字段
    private Long id;          // 对应 id
    private String title;     // 对应 title
    private String content;   // 对应 content
    private Date createTime;  // 对应 create_time

    // 无参构造函数
    public NewsEntity() {}

    // Getters 和 Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getContent() { 
        return content; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }

    public Date getCreateTime() { 
        return createTime; 
    }
    public void setCreateTime(Date createTime) { 
        this.createTime = createTime; 
    }
}