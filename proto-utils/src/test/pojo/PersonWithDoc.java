package com.volmyr.proto.model.test;

import java.util.List;
import java.util.Map;

/**
 * This class was created automatically from proto file "person.proto".
 */
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
    Person defaultInstance = Person.getDefaultInstance();
    return Person.newBuilder()
        .setFirstName(this.firstName != null
            ? this.firstName : defaultInstance.getFirstName())
        .setLastName(this.lastName != null
            ? this.lastName : defaultInstance.getLastName())
        .addAllPhones(this.phones != null
            ? this.phones : defaultInstance.getPhonesList())
        .putAllIds(this.ids != null
            ? this.ids : defaultInstance.getIdsMap())
        .build();
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
