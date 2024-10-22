package tw.org.organ.pojo.abstractClass;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class Articel {

//	@Schema(description = "類別ID")
//	@TableField("category_id")
//	protected Long categoryId;

//	@Schema(description = "文章的類型")
//	@TableField("type")
//	protected String type;
//
//	@Schema(description = "文章標題")
//	@TableField("title")
//	protected String title;
//
//	@Schema(description = "對文章的描述")
//	@TableField("description")
//	protected String description;
//
//	@Schema(description = "HTML文章內容")
//	@TableField("content")
//	protected String content;
//
//	@Schema(description = "封面縮圖URL")
//	@TableField("cover_thumbnail_url")
//	protected String coverThumbnailUrl;
//
//	@Schema(description = "瀏覽數")
//	@TableField("views")
//	protected Long views;
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@Schema(description = "創建時間")
//	@TableField(value = "create_time", fill = FieldFill.INSERT)
//	protected LocalDateTime createTime;
//
//	@Schema(description = "創建者")
//	@TableField(value = "create_by", fill = FieldFill.INSERT)
//	protected String createBy;
//
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@Schema(description = "最後更新時間")
//	@TableField(value = "update_time", fill = FieldFill.UPDATE)
//	protected LocalDateTime updateTime;
//
//	@Schema(description = "最後更新者")
//	@TableField(value = "update_by", fill = FieldFill.UPDATE)
//	protected String updateBy;
//
//	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
//	@TableField("is_deleted")
//	@TableLogic
//	protected String isDeleted;
//
//	// 讓子類去實現get ID的方式
//	public abstract Long getId();
//
//	// 讓子類去實現set ID的方式
//	public abstract void setId(Long id);

}
