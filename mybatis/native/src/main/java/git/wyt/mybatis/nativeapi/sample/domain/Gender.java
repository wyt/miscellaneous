package git.wyt.mybatis.nativeapi.sample.domain;

import lombok.Getter;

@Getter
public enum Gender {
  Male("男"),
  Female("女");

  Gender(String value) {
    this.value = value;
  }

  private String value;
}
