package com.volmyr.test.proto.model;

import java.util.Map;

public final class MessagePayloadPojo {
  private String name;

  private Map<String, String> params;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;
  }

  @Override
  public String toString() {
    return "MessagePayloadPojo{" +
        "name='" + name + '\'' +
        ", params=" + params +
        '}';
  }
}
