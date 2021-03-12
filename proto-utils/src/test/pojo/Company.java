package com.volmyr.proto.model.test.org.company;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.volmyr.proto.model.test.org.employee.EmployeePojo;
import java.util.List;
import java.util.Map;

public final class CompanyPojo {
  private Long id;

  private String name;

  private Map<String, Object> attributes;

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

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public List<EmployeePojo> getEmployees() {
    return employees;
  }

  public void setEmployees(List<EmployeePojo> employees) {
    this.employees = employees;
  }

  public Company convert() {
    return Company.newBuilder().build();
  }

  public static CompanyPojo convert(Company proto) {
    CompanyPojo pojo = new CompanyPojo();
    pojo.setId(proto.getId());
    pojo.setName(proto.getName());
    pojo.setAttributes(proto.getAttributesMap().entrySet().stream()
        .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> {
          try {
            return e.getValue().unpack(Message.class);
          } catch (InvalidProtocolBufferException ex) {
            throw new RuntimeException(ex);
          }
        })));
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
