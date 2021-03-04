package com.volmyr.proto.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.volmyr.proto.model.test.org.Employee;
import com.volmyr.proto.model.test.Person;
import com.volmyr.proto.utils.ProtoToPojo.Options;
import java.io.File;
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
        .generate().getResults())
        .containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(
            "com.volmyr.proto.model.test.PersonPojo",
            Files.asCharSource(new File("src/test/pojo/Person.java"), Charsets.UTF_8)
                .read()));
  }

  @Test
  public void shouldCreatePojoFromEmployeeProto() throws Exception {
    assertThat(new ProtoToPojo(Employee.class.getName(),
        Options.builder().withDefaultValues().build())
        .generate().getResults())
        .containsExactlyInAnyOrderEntriesOf(ImmutableMap.of(
            "com.volmyr.proto.model.test.org.AddressPojo",
            Files.asCharSource(new File("src/test/pojo/Address.java"), Charsets.UTF_8)
                .read(),
            "com.volmyr.proto.model.test.org.EmployeePojo",
            Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
                .read()
        ));
  }
}
