package com.volmyr.proto.model.test.org.employee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public final class EmployeePojo {
  private String firstName;

  private String lastName;

  private AddressPojo mainAddress;

  private List<AddressPojo> addresses;

  private Integer age;

  private Employee.State state;

  private Sex sex;

  private Long id;

  private Boolean active;

  private Map<String, Sex> attributes;

  private Map<String, AddressPojo> contacts;

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

  public AddressPojo getMainAddress() {
    return mainAddress;
  }

  public void setMainAddress(AddressPojo mainAddress) {
    this.mainAddress = mainAddress;
  }

  public List<AddressPojo> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressPojo> addresses) {
    this.addresses = addresses;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Employee.State getState() {
    return state;
  }

  public void setState(Employee.State state) {
    this.state = state;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Map<String, Sex> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Sex> attributes) {
    this.attributes = attributes;
  }

  public Map<String, AddressPojo> getContacts() {
    return contacts;
  }

  public void setContacts(Map<String, AddressPojo> contacts) {
    this.contacts = contacts;
  }

  public Employee convert() {
    Employee defaultInstance = Employee.getDefaultInstance();
    return Employee.newBuilder()
        .setFirstName(this.firstName != null
            ? this.firstName : defaultInstance.getFirstName())
        .setLastName(this.lastName != null
            ? this.lastName : defaultInstance.getLastName())
        .setMainAddress(this.mainAddress != null
            ? this.mainAddress.convert() : defaultInstance.getMainAddress())
        .addAllAddresses(this.addresses != null
            ? this.addresses.stream()
            .map(AddressPojo::convert)
            .collect(ImmutableList.toImmutableList())
            : defaultInstance.getAddressesList())
        .setAge(this.age != null
            ? this.age : defaultInstance.getAge())
        .setState(this.state != null
            ? this.state : defaultInstance.getState())
        .setSex(this.sex != null
            ? this.sex : defaultInstance.getSex())
        .setId(this.id != null
            ? this.id : defaultInstance.getId())
        .setActive(this.active != null
            ? this.active : defaultInstance.getActive())
        .putAllAttributes(this.attributes != null
            ? this.attributes : defaultInstance.getAttributesMap())
        .putAllContacts(this.contacts != null
            ? this.contacts.entrySet().stream()
            .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> e.getValue().convert()))
            : defaultInstance.getContactsMap())
        .build();
  }

  public static EmployeePojo convert(Employee proto) {
    EmployeePojo pojo = new EmployeePojo();
    pojo.setFirstName(proto.getFirstName());
    pojo.setLastName(proto.getLastName());
    pojo.setMainAddress(AddressPojo.convert(proto.getMainAddress()));
    pojo.setAddresses(proto.getAddressesList().stream()
        .map(AddressPojo::convert)
        .collect(ImmutableList.toImmutableList()));
    pojo.setAge(proto.getAge());
    pojo.setState(proto.getState());
    pojo.setSex(proto.getSex());
    pojo.setId(proto.getId());
    pojo.setActive(proto.getActive());
    pojo.setAttributes(proto.getAttributesMap());
    pojo.setContacts(proto.getContactsMap().entrySet().stream()
        .collect(ImmutableMap.toImmutableMap(
            Map.Entry::getKey,
            e -> AddressPojo.convert(e.getValue()))));
    return pojo;
  }

  @Override
  public String toString() {
    return "EmployeePojo{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", mainAddress=" + mainAddress +
        ", addresses=" + addresses +
        ", age=" + age +
        ", state=" + state +
        ", sex=" + sex +
        ", id=" + id +
        ", active=" + active +
        ", attributes=" + attributes +
        ", contacts=" + contacts +
        '}';
  }
}
