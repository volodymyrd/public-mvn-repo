package com.volmyr.proto.utils;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.builder;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getGetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getPrivateField;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getSetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.overrideToString;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.protobuf.Descriptors;
import com.google.protobuf.MessageOrBuilder;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.volmyr.java_source_utils.JavaPoetClassGenerator;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.Modifier;

/**
 * Create a plain java class from proto message.
 */
public final class ProtoToPojo {

  private static final TypeName STRING = TypeName.get(String.class);
  private final boolean preservingProtoFieldNames;
  private final String suffix;

  public ProtoToPojo() {
    this(false, "Pojo");
  }

  public ProtoToPojo(boolean preservingProtoFieldNames, String suffix) {
    this.preservingProtoFieldNames = preservingProtoFieldNames;
    this.suffix = suffix;
  }

  public String generate(MessageOrBuilder message) throws IOException, ClassNotFoundException {
    ImmutableList<Field> fields = extractFields(message);
    return generatePojo(
        message.getClass().getPackage().getName(),
        message.getDescriptorForType().getName() + suffix,
        fields);
  }

  private ImmutableList<Field> extractFields(MessageOrBuilder message)
      throws IOException, ClassNotFoundException {
    ImmutableList.Builder<Field> fieldsBuilder = ImmutableList.builder();
    for (Descriptors.FieldDescriptor field : message.getDescriptorForType().getFields()) {
//      if (field.isOptional()) {
//        if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE
//            && !message.hasField(field)) {
//          // Always skip empty optional message fields. If not we will recurse indefinitely if
//          // a message has itself as a sub-field.
//          continue;
//        }
//        Descriptors.OneofDescriptor oneof = field.getContainingOneof();
//        if (oneof != null && !message.hasField(field)) {
//          // Skip all oneof fields except the one that is actually set
//          continue;
//        }
//      }
      fieldsBuilder.add(addField(message, field));
    }
    return fieldsBuilder.build();
  }

//  private Field addField(Descriptors.FieldDescriptor field)
//      throws IOException, ClassNotFoundException {
//    return addField(field);
////    if (field.isMapField()) {
////      //printMapFieldValue(field, value);
////    } else if (field.isRepeated()) {
////      //printRepeatedFieldValue(field, value);
////    } else {
////      //printSingleFieldValue(field, value);
////    }
//  }

  private Field addField(MessageOrBuilder message, Descriptors.FieldDescriptor field)
      throws ClassNotFoundException {
    switch (field.getJavaType()) {
      case INT:
        return new Field(getFieldName(field), TypeName.get(Integer.class), "Integer", "");
      case LONG:
        return new Field(getFieldName(field), TypeName.get(Long.class), "Long", "");
      case FLOAT:
        return new Field(getFieldName(field), TypeName.get(Float.class), "Float", "");
      case DOUBLE:
        return new Field(getFieldName(field), TypeName.get(Double.class), "Double", "");
      case BOOLEAN:
        return new Field(getFieldName(field), TypeName.get(Boolean.class), "Boolean", "");
      case STRING:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, String.class),
              "List<String>",
              "");
        } else {
          return new Field(getFieldName(field), TypeName.get(String.class), "String", "");
        }
      case BYTE_STRING:
        throw new UnsupportedOperationException("Unsupported Java Type: BYTE_STRING");
      case ENUM:
        return new Field(
            getFieldName(field),
            TypeName.get(Class.forName(field.getEnumType().getFullName())),
            field.getEnumType().getName(),
            field.getEnumType().getFullName());
      case MESSAGE:
        String typeFullName = message.getClass().getPackage().getName()
            + "." + field.getMessageType().getName();
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Class.forName(typeFullName)),
              "List<" + field.getMessageType().getName() + ">",
              typeFullName);
        } else {
          return new Field(
              getFieldName(field),
              TypeName.get(Class.forName(typeFullName)),
              field.getMessageType().getName(),
              typeFullName);
        }
      default:
        throw new UnsupportedOperationException("Unsupported Java Type: " + field.getJavaType());
    }
  }

  private String getFieldName(Descriptors.FieldDescriptor field) {
    if (preservingProtoFieldNames) {
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
    private final String typeName;
    private final String fullType;

    public Field(String name, TypeName type, String typeName, String fullType) {
      this.name = name;
      this.type = type;
      this.typeName = typeName;
      this.fullType = fullType;
    }

    @Override
    public String toString() {
      return "Field{" +
          "name='" + name + '\'' +
          ", type=" + type +
          ", typeName='" + typeName + '\'' +
          ", fullType='" + fullType + '\'' +
          '}';
    }
  }
}
