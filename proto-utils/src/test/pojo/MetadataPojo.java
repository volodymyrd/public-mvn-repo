package com.volmyr.proto.model.test;

public final class MetadataPojo {
  private String prop;

  public String getProp() {
    return prop;
  }

  public void setProp(String prop) {
    this.prop = prop;
  }

  public Metadata convert() {
    Metadata defaultInstance = Metadata.getDefaultInstance();
    return Metadata.newBuilder()
        .setProp(this.prop != null
            ? this.prop : defaultInstance.getProp())
        .build();
  }

  public static MetadataPojo convert(Metadata proto) {
    MetadataPojo pojo = new MetadataPojo();
    pojo.setProp(proto.getProp());
    return pojo;
  }

  @Override
  public String toString() {
    return "MetadataPojo{" +
        "prop='" + prop + '\'' +
        '}';
  }
}
