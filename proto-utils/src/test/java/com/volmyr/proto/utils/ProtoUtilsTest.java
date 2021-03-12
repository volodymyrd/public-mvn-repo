package com.volmyr.proto.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.volmyr.proto.model.test.Person;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for {@link ProtoUtils}.
 */
public class ProtoUtilsTest {

  @Test
  public void shouldConvertProtoMessageToJsonString() throws IOException {
    assertThat(
        ProtoUtils.toJson(
            Person.newBuilder()
                .setFirstName("Name 1")
                .setLastName("Name 2")
                .build()))
        .isEqualTo("{\"firstName\":\"Name 1\",\"lastName\":\"Name 2\",\"phones\":[],\"ids\":{}}");
  }

  @Test
  public void shouldConvertJsonStringToProtoPerson() throws IOException {
    assertThat(
        ProtoUtils
            .fromJson("{\"firstName\":\"Name 1\",\"lastName\":\"Name 2\"}", Person.newBuilder()))
        .isEqualTo(
            Person.newBuilder()
                .setFirstName("Name 1")
                .setLastName("Name 2")
                .build());
  }
}
