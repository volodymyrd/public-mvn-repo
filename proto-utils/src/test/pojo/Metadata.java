package com.volmyr.proto.model.test;

public final class MetadataPojo {
  private Metadata1Pojo metadata1;

  private String prop;

  public Metadata1Pojo getMetadata1() {
    return metadata1;
  }

  public void setMetadata1(Metadata1Pojo metadata1) {
    this.metadata1 = metadata1;
  }

  public String getProp() {
    return prop;
  }

  public void setProp(String prop) {
    this.prop = prop;
  }

  public Metadata convert() {
    Metadata defaultInstance = Metadata.getDefaultInstance();
    return Metadata.newBuilder()
        .setMetadata1(this.metadata1 != null
            ? this.metadata1.convert() : defaultInstance.getMetadata1())
        .setProp(this.prop != null
            ? this.prop : defaultInstance.getProp())
        .build();
  }

  public static MetadataPojo convert(Metadata proto) {
    MetadataPojo pojo = new MetadataPojo();
    pojo.setMetadata1(Metadata1Pojo.convert(proto.getMetadata1()));
    pojo.setProp(proto.getProp());
    return pojo;
  }

  @Override
  public String toString() {
    return "MetadataPojo{" +
        "metadata1=" + metadata1 +
        ", prop='" + prop + '\'' +
        '}';
  }
}
