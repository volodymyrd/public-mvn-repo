package com.volmyr.proto.utils;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.squareup.javapoet.ClassName.OBJECT;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.builder;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getGetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getPrivateField;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getSetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.overrideToString;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.MessageOrBuilder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.volmyr.java_source_utils.JavaPoetClassGenerator;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;

/**
 * Create a plain java class from a proto message.
 */
public final class ProtoToPojo {

  @AutoValue
  public static abstract class Options {

    @Nullable
    public abstract String protoGeneratedFilesDir();

    public abstract boolean preservingProtoFieldNames();

    public abstract String prefix();

    public abstract String suffix();

    @Nullable
    public abstract String doc();

    public static Builder builder() {
      return new AutoValue_ProtoToPojo_Options.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

      default Builder withDefaultValues() {
        return preservingProtoFieldNames(false)
            .prefix("")
            .suffix("Pojo");
      }

      Builder protoGeneratedFilesDir(String protoGeneratedFilesDir);

      Builder preservingProtoFieldNames(boolean preservingProtoFieldNames);

      Builder prefix(String prefix);

      Builder suffix(String suffix);

      Builder doc(String doc);

      Options build();
    }
  }

  @AutoValue
  public static abstract class Result {

    public abstract String packageName();

    public abstract String className();

    public abstract String pojoFile();

    public static Builder builder() {
      return new AutoValue_ProtoToPojo_Result.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

      public abstract Builder packageName(String packageName);

      public abstract Builder className(String className);

      public abstract Builder pojoFile(String pojoFile);

      public abstract Result build();
    }
  }

  private static final TypeName STRING = TypeName.get(String.class);

  private final MessageOrBuilder root;
  private final Options options;
  private final String protoFileName;

  private final Map<String, Result> results = new HashMap<>();

  public ProtoToPojo(String messageClassName) throws Exception {
    this(messageClassName, Options.builder().withDefaultValues().build());
  }

  public ProtoToPojo(String messageClassName, Options options) throws Exception {
    this.options = options;
    this.root = getBuilder(messageClassName);
    this.protoFileName = root.getDescriptorForType().getFile().getFullName();
  }

  public ProtoToPojo generate() throws Exception {
    generate(root);
    return this;
  }

  public ImmutableList<Result> getResults() {
    return ImmutableList.copyOf(results.values());
  }

  private MessageOrBuilder getBuilder(String messageClassName) throws Exception {
    return (MessageOrBuilder) getTypeClass(messageClassName).getMethod("newBuilder").invoke(null);
  }

  private Class<?> getTypeClass(String messageClassName) throws Exception {
    if (!isNullOrEmpty(options.protoGeneratedFilesDir())) {
      File protoDir = new File(options.protoGeneratedFilesDir());
      if (!protoDir.exists() || !protoDir.isDirectory()) {
        throw new IllegalArgumentException("Not found a proto dir " + protoDir.getAbsolutePath());
      }
      URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread()
          .getContextClassLoader();
      Class<?> urlClass = URLClassLoader.class;
      Method method = urlClass.getDeclaredMethod("addURL", URL.class);
      method.setAccessible(true);
      method.invoke(urlClassLoader, protoDir.toURI().toURL());
      urlClassLoader.loadClass(messageClassName);
    }
    return Class.forName(messageClassName);
  }

  private void generate(MessageOrBuilder message) throws RuntimeException {
    String packageName = message.getDescriptorForType().getFile().getOptions().getJavaPackage();
    String className = options.prefix()
        + message.getDescriptorForType().getName()
        + options.suffix();
    ImmutableList<Field> fields = extractFields(packageName, message);
    results.put(
        packageName + "." + className,
        Result.builder()
            .packageName(packageName)
            .className(className)
            .pojoFile(generatePojo(packageName, className, fields))
            .build());
  }

  private ImmutableList<Field> extractFields(String packageName, MessageOrBuilder message)
      throws RuntimeException {
    return extractFields(packageName, message.getDescriptorForType().getFields());
  }

  private ImmutableList<Field> extractFields(String packageName, List<FieldDescriptor> descriptors)
      throws RuntimeException {
    return descriptors.stream()
        .map(field -> {
          try {
            return extractField(packageName, field);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        })
        .collect(toImmutableList());
  }

  private Field extractField(String packageName, Descriptors.FieldDescriptor field)
      throws Exception {
    // TODO: add support for Oneof: field.getContainingOneof()
    switch (field.getJavaType()) {
      case INT:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field), ParameterizedTypeName.get(List.class, Integer.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(Integer.class));
        }
      case LONG:
        if (field.isRepeated()) {
          return new Field(getFieldName(field), ParameterizedTypeName.get(List.class, Long.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(Long.class));
        }
      case FLOAT:
        if (field.isRepeated()) {
          return new Field(getFieldName(field), ParameterizedTypeName.get(List.class, Float.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(Float.class));
        }
      case DOUBLE:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field), ParameterizedTypeName.get(List.class, Double.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(Double.class));
        }
      case BOOLEAN:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field), ParameterizedTypeName.get(List.class, Boolean.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(Boolean.class));
        }
      case STRING:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field), ParameterizedTypeName.get(List.class, String.class));
        } else {
          return new Field(getFieldName(field), TypeName.get(String.class));
        }
      case BYTE_STRING:
        throw new UnsupportedOperationException("Unsupported Java Type: BYTE_STRING");
      case ENUM:
        String typeFullName = packageName + "." + field.getEnumType().getName();
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, getTypeClass(typeFullName)));
        } else {
          return new Field(getFieldName(field), TypeName.get(getTypeClass(typeFullName)));
        }
      case MESSAGE:
        if (field.isMapField()) {
          ImmutableList<Field> fields =
              extractFields(packageName, field.getMessageType().getFields());

          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(
                  ClassName.get(Map.class),
                  fields.stream().filter(f -> f.name.equals("key")).findFirst()
                      .orElseThrow(() -> new IllegalStateException("Not found key for map fields")).
                      type,
                  fields.stream().filter(f -> f.name.equals("value")).findFirst()
                      .orElseThrow(
                          () -> new IllegalStateException("Not found value for map fields"))
                      .type));
        } else if (field.getMessageType().getFullName().equals("google.protobuf.Any")) {
          return new Field(getFieldName(field), OBJECT);
        } else {
          String packageNameType = field.getMessageType().getFile().getOptions().getJavaPackage();
          String classNameTypeProto = field.getMessageType().getName();
          String classNameTypePojo = options.prefix() + classNameTypeProto + options.suffix();
          String typeFullNameProto = packageNameType + "." + classNameTypeProto;
          if (field.getMessageType().getFile().getFullName().equals(protoFileName)
              && results.get(typeFullNameProto) == null) {
            generate(getBuilder(typeFullNameProto));
          }
          ClassName className = ClassName.get(packageNameType, classNameTypePojo);
          if (field.isRepeated()) {
            return new Field(
                getFieldName(field),
                ParameterizedTypeName.get(ClassName.get(List.class), className));
          } else {
            return new Field(getFieldName(field), className);
          }
        }
      default:
        throw new UnsupportedOperationException("Unsupported Java Type: " + field.getJavaType());
    }
  }

  private String getFieldName(Descriptors.FieldDescriptor field) {
    if (options.preservingProtoFieldNames()) {
      return field.getName();
    } else {
      return field.getJsonName();
    }
  }

  private String generatePojo(String packageName, String className, ImmutableList<Field> fields) {
    Builder<MethodSpec> methodsBuilder = ImmutableList.builder();
    fields.forEach(f -> {
      methodsBuilder.add(getGetter(f.type, f.name));
      methodsBuilder.add(getSetter(f.type, f.name));
    });
    methodsBuilder.add(overrideToString(
        className,
        fields.stream().collect(toImmutableMap(f -> f.name, f -> f.type.equals(STRING)))));
    return new JavaPoetClassGenerator(packageName, true)
        .generate(builder(className)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addFields(fields.stream()
                .map(f -> getPrivateField(f.type, f.name))
                .collect(toImmutableList()))
            .addMethods(methodsBuilder.build())
            .addJavadoc(!isNullOrEmpty(options.doc()) ? options.doc() : "", protoFileName)
            .build());
  }

  private static class Field {

    private final String name;
    private final TypeName type;

    public Field(String name, TypeName type) {
      this.name = name;
      this.type = type;
    }
  }
}
