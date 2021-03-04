package com.volmyr.proto.utils;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.builder;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getGetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getPrivateField;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getSetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.overrideToString;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.Descriptors;
import com.google.protobuf.MessageOrBuilder;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.volmyr.java_source_utils.JavaPoetClassGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;

/**
 * Create a plain java class from proto message.
 */
public final class ProtoToPojo {

  @AutoValue
  public static abstract class Options {

    public abstract boolean preservingProtoFieldNames();

    public abstract String prefix();

    public abstract String suffix();

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

      Builder preservingProtoFieldNames(boolean preservingProtoFieldNames);

      Builder prefix(String prefix);

      Builder suffix(String suffix);

      Options build();
    }
  }

  private static final TypeName STRING = TypeName.get(String.class);

  private final MessageOrBuilder root;
  private final Options options;

  private final Map<String, String> results = new HashMap<>();

  public ProtoToPojo(String messageClassName, Options options) throws Exception {
    this.root = getBuilder(messageClassName);
    this.options = options;
  }

  public ProtoToPojo generate() throws Exception {
    generate(root);
    return this;
  }

  public Map<String, String> getResults() {
    return ImmutableMap.copyOf(results);
  }

  private static MessageOrBuilder getBuilder(String messageClassName) throws Exception {
    Class<?> messageClass = Class.forName(messageClassName);
    return (MessageOrBuilder) messageClass.getMethod("newBuilder").invoke(null);
  }

  private void generate(MessageOrBuilder message) throws Exception {
    String protoFileName = message.getDescriptorForType().getFile().getFullName();
    String packageName = message.getDescriptorForType().getFile().getOptions().getJavaPackage();
    String className = options.prefix()
        + message.getDescriptorForType().getName()
        + options.suffix();
    ImmutableList<Field> fields = extractFields(protoFileName, packageName, message);
    results.put(
        packageName + "." + className,
        generatePojo(packageName, className, fields));
  }

  private ImmutableList<Field> extractFields(
      String protoFileName,
      String packageName,
      MessageOrBuilder message)
      throws Exception {
    ImmutableList.Builder<Field> fieldsBuilder = ImmutableList.builder();
    for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
//        Descriptors.OneofDescriptor oneof = field.getContainingOneof();
//        if (oneof != null && !message.hasField(field)) {
//        }
//      }
      fieldsBuilder.add(addField(protoFileName, packageName, message, field));
    }
    return fieldsBuilder.build();
  }

////    if (field.isMapField()) {
////      //printMapFieldValue(field, value);
////    }

  private Field addField(
      String protoFileName,
      String packageName,
      MessageOrBuilder message,
      Descriptors.FieldDescriptor field)
      throws Exception {
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
              ParameterizedTypeName.get(List.class, Class.forName(typeFullName)));
        } else {
          return new Field(getFieldName(field), TypeName.get(Class.forName(typeFullName)));
        }
      case MESSAGE:
        typeFullName = packageName + "." + field.getMessageType().getName();
        if (field.getFile().getFullName().equals(protoFileName)
            && results.get(typeFullName) == null) {
          generate(getBuilder(typeFullName));
        }
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Class.forName(typeFullName)));
        } else {
          return new Field(
              getFieldName(field), TypeName.get(Class.forName(typeFullName)));
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
