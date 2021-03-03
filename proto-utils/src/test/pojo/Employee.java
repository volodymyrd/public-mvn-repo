package com.volmyr.proto.model.test;

import java.util.List;

public final class EmployeePojo {
  private String firstName;

  private String lastName;

  private Address mainAddress;

  private List<Address> addresses;

  private Integer age;

  private Sex sex;

  private Long id;

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

  public Address getMainAddress() {
    return mainAddress;
  }

  public void setMainAddress(Address mainAddress) {
    this.mainAddress = mainAddress;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
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

  @Override
  public String toString() {
    return "EmployeePojo{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", mainAddress=" + mainAddress +
        ", addresses=" + addresses +
        ", age=" + age +
        ", sex=" + sex +
        ", id=" + id +
        '}';
  }
}
