package com.volmyr.proto.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.volmyr.proto.model.test.Employee;
import com.volmyr.proto.model.test.Person;
import java.io.File;
import org.junit.Test;

/**
 * Tests for {@link ProtoToPojo}.
 */
public class ProtoToPojoTest {

  @Test
  public void shouldCreatePojoFromPersonProto() throws Exception {
    assertThat(new ProtoToPojo().generate(Person.newBuilder()))
        .isEqualTo(
            Files.asCharSource(new File("src/test/pojo/Person.java"), Charsets.UTF_8)
                .read());
  }

  @Test
  public void shouldCreatePojoFromEmployeeProto() throws Exception {
    assertThat(new ProtoToPojo().generate(Employee.newBuilder()))
        .isEqualTo(
            Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
                .read());
  }
}
