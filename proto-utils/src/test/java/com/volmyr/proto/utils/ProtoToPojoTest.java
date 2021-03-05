package com.volmyr.proto.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.volmyr.proto.model.test.Person;
import com.volmyr.proto.model.test.org.company.Company;
import com.volmyr.proto.model.test.org.employee.Employee;
import com.volmyr.proto.utils.ProtoToPojo.Options;
import java.io.File;
import java.util.Map;
import org.junit.Test;

/**
 * Tests for {@link ProtoToPojo}.
 */
public class ProtoToPojoTest {

  @Test
  public void shouldCreatePojoFromPersonProto() throws Exception {
    assertThat(new ProtoToPojo(
        Person.class.getName(),
        Options.builder().withDefaultValues().build())
        .generate()
        .getResults())
        .containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(
            "com.volmyr.proto.model.test.PersonPojo",
            Files.asCharSource(new File("src/test/pojo/Person.java"), Charsets.UTF_8)
                .read()));
  }

  @Test
  public void shouldCreatePojoFromCompanyProto() throws Exception {
    Map<String, String> results = new ProtoToPojo(
        Company.class.getName(),
        Options.builder().withDefaultValues().build())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);

    assertThat(results.get("com.volmyr.proto.model.test.org.company.CompanyPojo")).isEqualTo(
        Files.asCharSource(new File("src/test/pojo/Company.java"), Charsets.UTF_8)
            .read());
  }

  @Test
  public void shouldCreatePojoFromEmployeeProto() throws Exception {
    Map<String, String> results = new ProtoToPojo(
        Employee.class.getName(),
        Options.builder().withDefaultValues().build())
        .generate()
        .getResults();

    assertThat(results.get("com.volmyr.proto.model.test.org.employee.AddressPojo"))
        .isEqualTo(Files.asCharSource(new File("src/test/pojo/Address.java"), Charsets.UTF_8)
            .read());

    assertThat(results.get("com.volmyr.proto.model.test.org.employee.EmployeePojo"))
        .isEqualTo(Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
            .read());

    assertThat(results)
        .containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(
            "com.volmyr.proto.model.test.org.employee.AddressPojo",
            Files.asCharSource(new File("src/test/pojo/Address.java"), Charsets.UTF_8)
                .read(),
            "com.volmyr.proto.model.test.org.employee.EmployeePojo",
            Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
                .read()
        ));
  }
}
