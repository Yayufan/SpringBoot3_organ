package tw.org.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 年表-時間線
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Getter
@Setter
@TableName("time_line")
@Schema(name = "TimeLine", description = "年表-時間線")
public class TimeLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主鍵ID")
    @TableId("time_line_id")
    private Long timeLineId;

    @Schema(description = "ROC民國年，蒐集時要指定105年、106年這樣")
    @TableField("year")
    private Integer year;

    @Schema(description = "事件日期")
    @TableField("event_date")
    private LocalDate eventDate;

    @Schema(description = "描述")
    @TableField("description")
    private String description;

    @Schema(description = "多個檔案請用;分號，分隔圖檔路徑")
    @TableField("image_files")
    private String imageFiles;

    @Schema(description = "創建時間")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "創建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "最後更新時間")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "最後更新者")
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "邏輯刪除(0為存在,1為刪除)")
    @TableField("is_deleted")
    @TableLogic
    private String isDeleted;
}
