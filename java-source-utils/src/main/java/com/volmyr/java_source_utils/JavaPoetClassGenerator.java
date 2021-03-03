package com.volmyr.java_source_utils;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.Map;
import java.util.Map.Entry;
import javax.lang.model.element.Modifier;

/**
 * Java class generator utils, using JavaPoet library.
 */
public final class JavaPoetClassGenerator {

  private final String packageName;
  private final boolean skipJavaLangImports;

  public static TypeSpec.Builder builder(String className) {
    return TypeSpec.classBuilder(className);
  }

  public static MethodSpec overrideToString(String className, Map<String, Boolean> fields) {
    StringBuilder builder = new StringBuilder("\"" + className + "{");
    if (fields != null && !fields.isEmpty()) {
      builder.append("\"");
      int i = 0;
      for (Entry<String, Boolean> e : fields.entrySet()) {
        builder.append(" +\n");
        if (i == 0) {
          builder.append("\"");
        } else {
          builder.append("\", ");
        }
        builder
            .append(e.getKey())
            .append(e.getValue() ? "='\"" : "=\"")
            .append(" + ")
            .append(e.getKey())
            .append(e.getValue() ? " + " : "")
            .append(e.getValue() ? "'\\''" : "");
        i++;
      }
      builder
          .append(" +\n")
          .append("'}'");
    } else {
      builder.append("}\"");
    }
    return MethodSpec.methodBuilder("toString")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .returns(String.class)
        .addStatement("return $L", builder)
        .build();
  }

  public static MethodSpec getGetter(TypeName fieldType, String fieldName) {
    return MethodSpec.methodBuilder(fieldToMethod("get", fieldName))
        .addModifiers(Modifier.PUBLIC)
        .returns(fieldType)
        .addStatement("return $L", fieldName)
        .build();
  }

  public static MethodSpec getSetter(TypeName fieldType, String fieldName) {
    return MethodSpec.methodBuilder(fieldToMethod("set", fieldName))
        .addModifiers(Modifier.PUBLIC)
        .addParameter(fieldType, fieldName)
        .addStatement("this.$L = $L", fieldName, fieldName)
        .build();
  }

  static String fieldToMethod(String prefix, String fieldName) {
    return prefix
        + fieldName.substring(0, 1).toUpperCase()
        + fieldName.substring(1);
  }

  public static FieldSpec getPrivateField(TypeName fieldType, String fieldName) {
    return getPrivateField(fieldType, fieldName, false);
  }

  public static FieldSpec getPrivateField(TypeName fieldType, String fieldName, boolean isFinal) {
    if (isFinal) {
      return FieldSpec.builder(fieldType, fieldName)
          .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
          .build();
    }
    return FieldSpec.builder(fieldType, fieldName)
        .addModifiers(Modifier.PRIVATE)
        .build();
  }

  public JavaPoetClassGenerator(String packageName) {
    this(packageName, false);
  }

  public JavaPoetClassGenerator(String packageName, boolean skipJavaLangImports) {
    this.packageName = packageName;
    this.skipJavaLangImports = skipJavaLangImports;
  }

  public String generate(TypeSpec typeSpec) {
    return JavaFile.builder(packageName, typeSpec)
        .skipJavaLangImports(skipJavaLangImports)
        .build()
        .toString();
  }

  public String generate(String className, Iterable<MethodSpec> methods, Modifier... modifiers) {
    TypeSpec helloWorld = builder(className)
        .addModifiers(modifiers)
        .addMethods(methods)
        .build();

    return JavaFile.builder(packageName, helloWorld)
        .skipJavaLangImports(skipJavaLangImports)
        .build()
        .toString();
  }
}
