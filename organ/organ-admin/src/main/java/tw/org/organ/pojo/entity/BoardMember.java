package tw.org.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 理監事名單，包含理事長、常務理事、理事、常務監事、監事等..
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Getter
@Setter
@TableName("board_member")
@Schema(name = "BoardMember", description = "理監事名單，包含理事長、常務理事、理事、常務監事、監事等..")
public class BoardMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主鍵ID")
    @TableId("board_member_id")
    private Long boardMemberId;

    @Schema(description = "第幾屆（數字，方便查詢和排序）")
    @TableField("term_number")
    private Integer termNumber;

    @Schema(description = "第幾屆（中文，用於展示）")
    @TableField("term_number_cn")
    private String termNumberCn;

    @Schema(description = "任職起始日期，直接寫民國年")
    @TableField("start_date")
    private String startDate;

    @Schema(description = "任職結束日期，直接寫民國年")
    @TableField("end_date")
    private String endDate;

    @Schema(description = "理事長，僅1位")
    @TableField("chairperson")
    private String chairperson;

    @Schema(description = "常務理事，多位，請用全形頓號分隔")
    @TableField("executive_directors")
    private String executiveDirectors;

    @Schema(description = "理事，多位，請用全形頓號分隔")
    @TableField("directors")
    private String directors;

    @Schema(description = "常務監事，僅1位")
    @TableField("executive_supervisor")
    private String executiveSupervisor;

    @Schema(description = "監事，多位，請用全形頓號分隔")
    @TableField("supervisors")
    private String supervisors;

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
