package git.wyt.mybatis.nativeapi.sample.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/** 身份证实体 */
@Setter
@Getter
public class IDCard {

  private int id;

  /** 姓名 */
  private String name;

  /** 性别 */
  private Gender gender;

  /** 出生日期 */
  private Date birthDate;

  /** 住址 */
  private String residentialAddress;

  /** 公民身份证号 */
  private String idCardNum;

  /** 签发机关 */
  private String IssuingAuthority;

  /** 有效期限,开始日期 */
  private Date expireDateStart;

  /** 有效期限,截止日期 */
  private Date expireDateEnd;
}
