package com.volmyr.proto.model.test;

import java.util.List;
import java.util.Map;

public final class PersonExtPojo {
  private String firstName;

  private String lastName;

  private List<String> phones;

  private Map<String, String> ids;

  private MetadataPojo metadata;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<String> getPhones() {
    return phones;
  }

  public void setPhones(List<String> phones) {
    this.phones = phones;
  }

  public Map<String, String> getIds() {
    return ids;
  }

  public void setIds(Map<String, String> ids) {
    this.ids = ids;
  }

  public MetadataPojo getMetadata() {
    return metadata;
  }

  public void setMetadata(MetadataPojo metadata) {
    this.metadata = metadata;
  }

  public PersonExt convert() {
    PersonExt defaultInstance = PersonExt.getDefaultInstance();
    return PersonExt.newBuilder()
        .setFirstName(this.firstName != null
            ? this.firstName : defaultInstance.getFirstName())
        .setLastName(this.lastName != null
            ? this.lastName : defaultInstance.getLastName())
        .addAllPhones(this.phones != null
            ? this.phones : defaultInstance.getPhonesList())
        .putAllIds(this.ids != null
            ? this.ids : defaultInstance.getIdsMap())
        .setMetadata(this.metadata != null
            ? this.metadata.convert() : defaultInstance.getMetadata())
        .build();
  }

  public static PersonExtPojo convert(PersonExt proto) {
    PersonExtPojo pojo = new PersonExtPojo();
    pojo.setFirstName(proto.getFirstName());
    pojo.setLastName(proto.getLastName());
    pojo.setPhones(proto.getPhonesList());
    pojo.setIds(proto.getIdsMap());
    pojo.setMetadata(MetadataPojo.convert(proto.getMetadata()));
    return pojo;
  }

  @Override
  public String toString() {
    return "PersonExtPojo{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", phones=" + phones +
        ", ids=" + ids +
        ", metadata=" + metadata +
        '}';
  }
}
