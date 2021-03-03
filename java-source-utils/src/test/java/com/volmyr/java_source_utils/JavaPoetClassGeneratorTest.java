package com.volmyr.java_source_utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.squareup.javapoet.MethodSpec;
import javax.lang.model.element.Modifier;
import org.junit.Test;

/**
 * Tests for {@link JavaPoetClassGenerator}.
 */
public class JavaPoetClassGeneratorTest {

  @Test
  public void shouldGenerateHelloWorldClass() {
    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, "args")
        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
        .build();

    assertThat(new JavaPoetClassGenerator("com.example.helloworld", true)
        .generate("HelloWorld", ImmutableList.of(main), Modifier.PUBLIC, Modifier.FINAL))
        .isEqualTo("package com.example.helloworld;\n"
            + "\n"
            + "public final class HelloWorld {\n"
            + "  public static void main(String[] args) {\n"
            + "    System.out.println(\"Hello, JavaPoet!\");\n"
            + "  }\n"
            + "}\n");
  }

  @Test
  public void shouldGenerateOverridingFor_ToString_WithNoFields() {
    assertThat(JavaPoetClassGenerator
        .overrideToString("Person", ImmutableMap.of()).toString())
        .isEqualTo("@java.lang.Override\n"
            + "public java.lang.String toString() {\n"
            + "  return \"Person{}\";\n"
            + "}\n");
  }

  @Test
  public void shouldGenerateOverridingFor_ToString_WithOneField() {
    assertThat(JavaPoetClassGenerator.overrideToString(
        "Person", ImmutableMap.of("name", true)).toString())
        .isEqualTo("@java.lang.Override\n"
            + "public java.lang.String toString() {\n"
            + "  return \"Person{\" +\n"
            + "      \"name='\" + name + '\\'' +\n"
            + "      '}';\n"
            + "}\n");
  }

  @Test
  public void shouldGenerateOverridingFor_ToString_WithMoreThanOneFields() {
    assertThat(JavaPoetClassGenerator.overrideToString(
        "Person", ImmutableMap.of("firstName", true, "lastName", true)).toString())
        .isEqualTo("@java.lang.Override\n"
            + "public java.lang.String toString() {\n"
            + "  return \"Person{\" +\n"
            + "      \"firstName='\" + firstName + '\\'' +\n"
            + "      \", lastName='\" + lastName + '\\'' +\n"
            + "      '}';\n"
            + "}\n");
  }
}
