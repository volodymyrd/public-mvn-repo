package com.volmyr.proto.model.test.org.company;

import com.google.common.collect.ImmutableList;
import com.volmyr.proto.model.test.org.employee.EmployeePojo;
import java.util.List;
import java.util.Map;

public final class CompanyPojo {
  private Long id;

  private String name;

  private Map<String, Boolean> attributes;

  private List<EmployeePojo> employees;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Boolean> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Boolean> attributes) {
    this.attributes = attributes;
  }

  public List<EmployeePojo> getEmployees() {
    return employees;
  }

  public void setEmployees(List<EmployeePojo> employees) {
    this.employees = employees;
  }

  public Company convert() {
    Company defaultInstance = Company.getDefaultInstance();
    return Company.newBuilder()
        .setId(this.id != null
            ? this.id : defaultInstance.getId())
        .setName(this.name != null
            ? this.name : defaultInstance.getName())
        .putAllAttributes(this.attributes != null
            ? this.attributes : defaultInstance.getAttributesMap())
        .addAllEmployees(this.employees != null
            ? this.employees.stream()
            .map(EmployeePojo::convert)
            .collect(ImmutableList.toImmutableList())
            : defaultInstance.getEmployeesList())
        .build();
  }

  public static CompanyPojo convert(Company proto) {
    CompanyPojo pojo = new CompanyPojo();
    pojo.setId(proto.getId());
    pojo.setName(proto.getName());
    pojo.setAttributes(proto.getAttributesMap());
    pojo.setEmployees(proto.getEmployeesList().stream()
        .map(EmployeePojo::convert)
        .collect(ImmutableList.toImmutableList()));
    return pojo;
  }

  @Override
  public String toString() {
    return "CompanyPojo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", attributes=" + attributes +
        ", employees=" + employees +
        '}';
  }
}
