package mybatis.plus.samples.new_xdt.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGenerator
 * @since 2022-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NewXdt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 主要内容
     */
    private String content;

    /**
     * 内容类型
     */
    private Integer type;

    /**
     * 内容时效状态，鲜货/中性/干货     
     */
    private Integer freshStatus;

    /**
     * 一级来源(发布/同步/抓取/机器写作)
     */
    private Integer firstSource;

    /**
     * 二级来源
     */
    private Integer secondSource;

    /**
     * 原业务线对应的内容类型
     */
    private Integer thirdBusinessType;

    /**
     * 第三方业务线主键id
     */
    private Long thirdBusinessId;

    /**
     * 内容评级 S、A、B、C、Z 、未定级
     */
    private Integer level;

    /**
     * 内容用户id
     */
    private Long userId;

    /**
     * 用户角色(用户侧角色体系)
     */
    private Integer userRole;

    /**
     * 锁定状态
     */
    private Integer lockStatus;

    /**
     * 标签(算法推荐侧)
     */
    private String tagId;

    /**
     * 关联活动id(都有，只有视频双向同步)
     */
    private String playId;

    /**
     * 话题id
     */
    private String talkId;

    /**
     * IP地址(加密后)
     */
    private String ip;

    /**
     * 全部车型信息
     */
    private String allModel;

    /**
     * 主车型信息
     */
    private String mainModel;

    /**
     * 摩托车车型信息
     */
    private String motorModel;

    /**
     * 车款id(同步老点评业务线款字段，只有老点评使用)
     */
    private Integer vehicle;

    /**
     * 内容图片数量
     */
    private Integer attachmentNum;

    /**
     * 内容数量
     */
    private Integer wordCount;

    /**
     * 记录内容变化时间
     */
    private LocalDateTime updateStime;

    /**
     * 打标状态，0未打标 1已打标 2已复审
     */
    private Integer markStatus;

    /**
     * 打标时间
     */
    private LocalDateTime markTime;

    /**
     * 打标用户id
     */
    private Long markOperatorId;

    /**
     * 打标用户name
     */
    private String markOperatorName;

    /**
     * 内容审核状态 -1 审核不通过，0未审核，1审核通过
     */
    private Integer verifyStatus;

    /**
     * 用户删除状态 0未删除  1已删除
     */
    private Integer userDeleteStatus;

    /**
     * 审核时间
     */
    private LocalDateTime verifyTime;

    /**
     * 内容创建时间
     */
    private LocalDateTime createStime;

    /**
     * 内容修改时间
     */
    private LocalDateTime userUpdateStime;

    /**
     * 运营编辑时间
     */
    private LocalDateTime operatorUpdateStime;

    /**
     * 封面图地址
     */
    private String coverImage;

    /**
     * 帖子链接地址
     */
    private String contentUrl;

    /**
     * 最后操作人
     */
    private String lastOperaotrId;

    /**
     * 最后操作时间
     */
    private String lastOpratorTime;


}
