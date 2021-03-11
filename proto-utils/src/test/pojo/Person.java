package com.volmyr.proto.model.test;

import java.util.List;
import java.util.Map;

public final class PersonPojo {
  private String firstName;

  private String lastName;

  private List<String> phones;

  private Map<String, String> ids;

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

  public Person convert() {
    return Person.newBuilder().build();
  }

  public static PersonPojo convert(Person proto) {
    PersonPojo pojo = new PersonPojo();
    pojo.setFirstName(proto.getFirstName());
    pojo.setLastName(proto.getLastName());
    pojo.setPhones(proto.getPhonesList());
    pojo.setIds(proto.getIdsMap());
    return pojo;
  }

  @Override
  public String toString() {
    return "PersonPojo{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", phones=" + phones +
        ", ids=" + ids +
        '}';
  }
}
