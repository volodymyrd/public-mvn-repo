package com.volmyr.proto.model.test;

public final class Metadata1Pojo {
  private String prop;

  public String getProp() {
    return prop;
  }

  public void setProp(String prop) {
    this.prop = prop;
  }

  public Metadata1 convert() {
    Metadata1 defaultInstance = Metadata1.getDefaultInstance();
    return Metadata1.newBuilder()
        .setProp(this.prop != null
            ? this.prop : defaultInstance.getProp())
        .build();
  }

  public static Metadata1Pojo convert(Metadata1 proto) {
    Metadata1Pojo pojo = new Metadata1Pojo();
    pojo.setProp(proto.getProp());
    return pojo;
  }

  @Override
  public String toString() {
    return "Metadata1Pojo{" +
        "prop='" + prop + '\'' +
        '}';
  }
}
