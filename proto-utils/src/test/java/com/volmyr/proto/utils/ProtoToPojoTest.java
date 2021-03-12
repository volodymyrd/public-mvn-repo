package com.volmyr.proto.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.volmyr.proto.model.test.Person;
import com.volmyr.proto.model.test.org.company.Company;
import com.volmyr.proto.model.test.org.employee.Employee;
import com.volmyr.proto.utils.ProtoToPojo.Options;
import java.io.File;
import org.junit.Test;

/**
 * Tests for {@link ProtoToPojo}.
 */
public class ProtoToPojoTest {

  @Test
  public void shouldCreatePojoFromPersonProto() throws Exception {
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(Person.class.getName())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);
    assertThat(results.get(0)).isEqualTo(
        ProtoToPojo.Result.builder()
            .packageName("com.volmyr.proto.model.test")
            .className("PersonPojo")
            .pojoFile(Files.asCharSource(new File("src/test/pojo/Person.java"), Charsets.UTF_8)
                .read())
            .build());
  }

  @Test
  public void shouldCreatePojoWithDocFromPersonProto() throws Exception {
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(Person.class.getName(),
        Options.builder()
            .withDefaultValues()
            .doc("This class was created automatically from proto file $S.")
            .build())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);
    assertThat(results.get(0)).isEqualTo(
        ProtoToPojo.Result.builder()
            .packageName("com.volmyr.proto.model.test")
            .className("PersonPojo")
            .pojoFile(
                Files.asCharSource(new File("src/test/pojo/PersonWithDoc.java"), Charsets.UTF_8)
                    .read())
            .build());
  }

  @Test
  public void shouldCreatePojoFromCompanyProto() throws Exception {
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(Company.class.getName())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);
    assertThat(results.get(0)).isEqualTo(
        ProtoToPojo.Result.builder()
            .packageName("com.volmyr.proto.model.test.org.company")
            .className("CompanyPojo")
            .pojoFile(Files.asCharSource(new File("src/test/pojo/Company.java"), Charsets.UTF_8)
                .read())
            .build());
  }

  @Test
  public void shouldCreatePojoFromEmployeeProto() throws Exception {
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(Employee.class.getName())
        .generate()
        .getResults();

    assertThat(results).hasSize(2);

    assertThat(results.stream().filter(e -> e.className().equals("AddressPojo"))
        .findFirst()
        .orElseThrow(IllegalStateException::new))
        .isEqualTo(
            ProtoToPojo.Result.builder()
                .packageName("com.volmyr.proto.model.test.org.employee")
                .className("AddressPojo")
                .pojoFile(Files.asCharSource(new File("src/test/pojo/Address.java"), Charsets.UTF_8)
                    .read())
                .build());

    assertThat(results.stream().filter(e -> e.className().equals("EmployeePojo"))
        .findFirst()
        .orElseThrow(IllegalStateException::new))
        .isEqualTo(
            ProtoToPojo.Result.builder()
                .packageName("com.volmyr.proto.model.test.org.employee")
                .className("EmployeePojo")
                .pojoFile(
                    Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
                        .read())
                .build());

    assertThat(results)
        .containsExactlyElementsOf(ImmutableList.of(
            ProtoToPojo.Result.builder()
                .packageName("com.volmyr.proto.model.test.org.employee")
                .className("AddressPojo")
                .pojoFile(Files.asCharSource(new File("src/test/pojo/Address.java"), Charsets.UTF_8)
                    .read())
                .build(),
            ProtoToPojo.Result.builder()
                .packageName("com.volmyr.proto.model.test.org.employee")
                .className("EmployeePojo")
                .pojoFile(
                    Files.asCharSource(new File("src/test/pojo/Employee.java"), Charsets.UTF_8)
                        .read())
                .build()
        ));
  }

  @Test
  public void shouldCreatePojoFromExternalProto() throws Exception {
    File protoDir = new File("../proto-test-model/target/classes/java/main");
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(
        "com.volmyr.test.proto.model.MessagePayload",
        Options.builder()
            .protoCompiledDirOrJar(protoDir.getAbsolutePath())
            .withDefaultValues()
            .build())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);
    assertThat(results.get(0)).isEqualTo(
        ProtoToPojo.Result.builder()
            .packageName("com.volmyr.test.proto.model")
            .className("MessagePayloadPojo")
            .pojoFile(Files
                .asCharSource(new File("src/test/pojo/MessagePayload.java"), Charsets.UTF_8)
                .read())
            .build());
  }

  @Test
  public void shouldCreatePojoFromExternalJarProto() throws Exception {
    File protoDir = new File(
        "../proto-test-model/target/libs/proto-test-model-0.0.28-SNAPSHOT.jar");
    ImmutableList<ProtoToPojo.Result> results = new ProtoToPojo(
        "com.volmyr.test.proto.model.MessagePayload",
        Options.builder()
            .protoCompiledDirOrJar(protoDir.getAbsolutePath())
            .withDefaultValues()
            .build())
        .generate()
        .getResults();

    assertThat(results).hasSize(1);
    assertThat(results.get(0)).isEqualTo(
        ProtoToPojo.Result.builder()
            .packageName("com.volmyr.test.proto.model")
            .className("MessagePayloadPojo")
            .pojoFile(Files
                .asCharSource(new File("src/test/pojo/MessagePayload.java"), Charsets.UTF_8)
                .read())
            .build());
  }
}
