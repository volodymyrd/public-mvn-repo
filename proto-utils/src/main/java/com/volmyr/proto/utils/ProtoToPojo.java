package com.volmyr.proto.utils;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.builder;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.fieldToMethod;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getGetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getPrivateField;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.getSetter;
import static com.volmyr.java_source_utils.JavaPoetClassGenerator.overrideToString;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Value;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
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
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;

/**
 * Create a plain java class from a proto message.
 */
public final class ProtoToPojo {

  @AutoValue
  public static abstract class Options {

    @Nullable
    public abstract String protoCompiledDirOrJar();

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

      Builder protoCompiledDirOrJar(String protoCompiledDirOrJar);

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

  private static final TypeName BOOLEAN = TypeName.get(Boolean.class);
  private static final TypeName DOUBLE = TypeName.get(Double.class);
  private static final TypeName INTEGER = TypeName.get(Integer.class);
  private static final TypeName FLOAT = TypeName.get(Float.class);
  private static final TypeName LONG = TypeName.get(Long.class);
  private static final TypeName STRING = TypeName.get(String.class);

  private static final ClassName MAP = ClassName.get(Map.class);
  private static final ClassName LIST = ClassName.get(List.class);

  private final Class<?> rootProtoClass;
  private final MessageOrBuilder root;
  private final Options options;
  private final String protoFileName;

  private final Map<String, Result> results = new HashMap<>();

  public ProtoToPojo(String messageClassName) throws Exception {
    this(messageClassName, Options.builder().withDefaultValues().build());
  }

  public ProtoToPojo(String messageClassName, Options options) throws Exception {
    this.options = options;
    this.rootProtoClass = getTypeClass(messageClassName);
    this.root = getBuilder(this.rootProtoClass);
    this.protoFileName = root.getDescriptorForType().getFile().getFullName();
  }

  public ProtoToPojo generate() throws Exception {
    generate(rootProtoClass.getSimpleName(), root);
    return this;
  }

  public ImmutableList<Result> getResults() {
    return ImmutableList.copyOf(results.values());
  }

  private static MessageOrBuilder getBuilder(Class<?> messageClass) throws Exception {
    return (MessageOrBuilder) messageClass.getMethod("newBuilder").invoke(null);
  }

  private MessageOrBuilder getBuilder(String messageClassName) throws Exception {
    return getBuilder(getTypeClass(messageClassName));
  }

  private Class<?> getTypeClass(String messageClassName) throws Exception {
    if (!isNullOrEmpty(options.protoCompiledDirOrJar())) {
      File protoDir = new File(options.protoCompiledDirOrJar());
      if (!protoDir.exists()) {
        throw new IllegalArgumentException(
            "Not found a proto dir or jar file" + protoDir.getAbsolutePath());
      }
      URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread()
          .getContextClassLoader();
      Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      method.setAccessible(true);
      method.invoke(urlClassLoader, protoDir.toURI().toURL());

      urlClassLoader.loadClass(messageClassName);
    }
    return Class.forName(messageClassName);
  }

  private void generate(String protoClassName, MessageOrBuilder message) throws RuntimeException {
    String packageName = message.getDescriptorForType().getFile().getOptions().getJavaPackage();
    String pojoClassName = options.prefix()
        + message.getDescriptorForType().getName()
        + options.suffix();
    ImmutableList<Field> fields = extractFields(packageName, message);
    results.put(
        packageName + "." + pojoClassName,
        Result.builder()
            .packageName(packageName)
            .className(pojoClassName)
            .pojoFile(generatePojo(packageName, protoClassName, pojoClassName, fields))
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
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Integer.class));
        } else {
          return new Field(getFieldName(field), INTEGER);
        }
      case LONG:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Long.class));
        } else {
          return new Field(getFieldName(field), LONG);
        }
      case FLOAT:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Float.class));
        } else {
          return new Field(getFieldName(field), FLOAT);
        }
      case DOUBLE:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Double.class));
        } else {
          return new Field(getFieldName(field), DOUBLE);
        }
      case BOOLEAN:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, Boolean.class));
        } else {
          return new Field(getFieldName(field), BOOLEAN);
        }
      case STRING:
        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, String.class));
        } else {
          return new Field(getFieldName(field), STRING);
        }
      case BYTE_STRING:
        throw new UnsupportedOperationException("Unsupported Java Type: BYTE_STRING");
      case ENUM:
        String typeFullName;
        if (field.getEnumType().getContainingType() != null
            && !field.getEnumType().getContainingType().getName().isEmpty()) {
          typeFullName = packageName + "."
              + field.getEnumType().getContainingType().getName() + "$"
              + field.getEnumType().getName();
        } else {
          typeFullName = packageName + "." + field.getEnumType().getName();
        }

        if (field.isRepeated()) {
          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(List.class, getTypeClass(typeFullName)));
        } else {
          return new Field(
              getFieldName(field), TypeName.get(getTypeClass(typeFullName)));
        }
      case MESSAGE:
        if (field.isMapField()) {
          ImmutableList<Field> fields =
              extractFields(packageName, field.getMessageType().getFields());

          return new Field(
              getFieldName(field),
              ParameterizedTypeName.get(
                  MAP,
                  fields.stream().filter(f -> f.name.equals("key")).findFirst()
                      .orElseThrow(() -> new IllegalStateException("Not found key for map fields")).
                      type,
                  fields.stream().filter(f -> f.name.equals("value")).findFirst()
                      .orElseThrow(
                          () -> new IllegalStateException("Not found value for map fields"))
                      .type));
        } else if (field.getMessageType().getFullName().equals("google.protobuf.Any")) {
          throw new UnsupportedOperationException("Not supported proto type: google.protobuf.Any");
          //return new Field(getFieldName(field), OBJECT);
        } else {
          String packageNameType = field.getMessageType().getFile().getOptions().getJavaPackage();
          // TODO nested type: field.getMessageType().getFile().getContainingType() != null
          String classNameTypeProto = field.getMessageType().getName();
          String classNameTypePojo = options.prefix() + classNameTypeProto + options.suffix();
          String typeFullNameProto = packageNameType + "." + classNameTypeProto;
          if (field.getMessageType().getFile().getFullName().equals(protoFileName)
              && results.get(typeFullNameProto) == null) {
            generate(classNameTypeProto, getBuilder(typeFullNameProto));
          }
          ClassName className = ClassName.get(packageNameType, classNameTypePojo);
          if (field.isRepeated()) {
            return new Field(
                getFieldName(field),
                ParameterizedTypeName.get(LIST, className));
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

  private String generatePojo(
      String packageName,
      String protoClassName,
      String pojoClassName,
      ImmutableList<Field> fields) {
    Builder<MethodSpec> methodsBuilder = ImmutableList.builder();
    fields.forEach(f -> {
      methodsBuilder.add(getGetter(f.type, f.name));
      methodsBuilder.add(getSetter(f.type, f.name));
    });
    methodsBuilder.add(
        buildConvertToProtoMethod(packageName, protoClassName, fields));
    methodsBuilder.add(
        buildConvertFromProtoMethod(packageName, protoClassName, pojoClassName, fields));
    methodsBuilder.add(overrideToString(
        pojoClassName,
        fields.stream().collect(toImmutableMap(f -> f.name, f -> f.type.equals(STRING)))));

    return new JavaPoetClassGenerator(packageName, true)
        .generate(builder(pojoClassName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addFields(fields.stream()
                .map(f -> getPrivateField(f.type, f.name))
                .collect(toImmutableList()))
            .addMethods(methodsBuilder.build())
            .addJavadoc(!isNullOrEmpty(options.doc()) ? options.doc() : "", protoFileName)
            .build());
  }

  private MethodSpec buildConvertToProtoMethod(
      String packageName,
      String protoClassName,
      ImmutableList<Field> fields) {
    ClassName protoClass = ClassName.get(packageName, protoClassName);

    String defaultInstance = "defaultInstance";

    MethodSpec.Builder builder = MethodSpec.methodBuilder("convert")
        .addModifiers(Modifier.PUBLIC)
        .returns(protoClass)
        .addStatement("$L $L = $L.getDefaultInstance()",
            protoClassName, defaultInstance, protoClassName);

    CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
    codeBlockBuilder.add("return $L.newBuilder()", protoClassName);
    fields.forEach(f -> {
      codeBlockBuilder.add("\n");
      if (f.type instanceof ParameterizedTypeName
          && ((ParameterizedTypeName) f.type).rawType.equals(LIST)) {
        TypeName type = ((ParameterizedTypeName) f.type).typeArguments.get(0);
        if (isPojo(type.toString(), options)) {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("addAll", f.name), f.name)
              .add("    ? this.$L.stream()\n", f.name)
              .add("    .map($T::convert)\n", type)
              .add("    .collect($T.toImmutableList())\n", ImmutableList.class)
              .add("    : $L.$L())", defaultInstance, fieldToMethod("get", f.name, "List"));
        } else {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("addAll", f.name), f.name)
              .add("    ? this.$L : ", f.name)
              .add("$L.$L())", defaultInstance, fieldToMethod("get", f.name, "List"));
        }
      } else if (f.type instanceof ParameterizedTypeName
          && ((ParameterizedTypeName) f.type).rawType.equals(MAP)) {
        TypeName type = ((ParameterizedTypeName) f.type).typeArguments.get(1);
        if (isPojo(type.toString(), options)) {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("putAll", f.name), f.name)
              .add("    ? this.$L.entrySet().stream()\n", f.name)
              .add("    .collect($T.toImmutableMap(", ImmutableMap.class)
              .add("$T::getKey, e -> e.getValue().convert()))\n", Entry.class)
              .add("    : $L.$L())", defaultInstance, fieldToMethod("get", f.name, "Map"))
              .build();
        } else if (type.equals(TypeName.OBJECT)) {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("putAll", f.name), f.name)
              .add("    ? this.$L.entrySet().stream()\n", f.name)
              .add("    .collect($T.toImmutableMap(", ImmutableMap.class)
              .add("$T::getKey, e -> $T.pack($T.newBuilder().build())))\n",
                  Entry.class, Any.class, Value.class)
              .add("    : $L.$L())", defaultInstance, fieldToMethod("get", f.name, "Map"))
              .build();
        } else {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("putAll", f.name), f.name)
              .add("    ? this.$L : ", f.name)
              .add("$L.$L())", defaultInstance, fieldToMethod("get", f.name, "Map"));
        }
      } else {
        if (isPojo(f.type.toString(), options)) {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("set", f.name), f.name)
              .add("    ? this.$L.convert() : ", f.name)
              .add("$L.$L())", defaultInstance, fieldToMethod("get", f.name));
        } else {
          codeBlockBuilder
              .add(".$L(this.$L != null\n", fieldToMethod("set", f.name), f.name)
              .add("    ? this.$L : ", f.name)
              .add("$L.$L())", defaultInstance, fieldToMethod("get", f.name));
        }
      }
    });
    codeBlockBuilder.add("\n.build()");
    return builder
        .addStatement(codeBlockBuilder.build())
        .build();
  }

  private MethodSpec buildConvertFromProtoMethod(
      String packageName,
      String protoClassName,
      String pojoClassName,
      ImmutableList<Field> fields) {
    String proto = "proto";
    String pojo = "pojo";
    ClassName protoClass = ClassName.get(packageName, protoClassName);
    ClassName pojoClass = ClassName.get(packageName, pojoClassName);

    List<CodeBlock> codeBlocks = fields.stream().map(f -> {
      if (f.type instanceof ParameterizedTypeName
          && ((ParameterizedTypeName) f.type).rawType.equals(MAP)) {
        TypeName type = ((ParameterizedTypeName) f.type).typeArguments.get(1);
        if (isPojo(type.toString(), options)) {
          return CodeBlock.builder()
              .add("$L.$L($L.$L()",
                  pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name, "Map"))
              .add(".entrySet().stream()\n")
              .add(".collect($T.toImmutableMap(\n", ImmutableMap.class)
              .add("    $T::getKey,\n", Entry.class)
              .add("    e -> $T.convert(e.getValue()))))", type)
              .build();
        } else if (type.equals(TypeName.OBJECT)) {
          return CodeBlock.builder()
              .add("$L.$L($L.$L()",
                  pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name, "Map"))
              .add(".entrySet().stream()\n")
              .add(".collect($T.toImmutableMap($T::getKey, e -> {\n",
                  ImmutableMap.class, Entry.class)
              .add("  try {\n")
              .add("    return e.getValue().unpack($T.class);\n", Message.class)
              .add("  } catch ($T ex) {\n", InvalidProtocolBufferException.class)
              .add("    throw new $T(ex);\n", RuntimeException.class)
              .add("  }\n")
              .add("})))")
              .build();
        }
        return CodeBlock.builder()
            .add("$L.$L($L.$L())",
                pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name, "Map"))
            .build();
      } else if (f.type instanceof ParameterizedTypeName
          && ((ParameterizedTypeName) f.type).rawType.equals(LIST)) {
        TypeName type = ((ParameterizedTypeName) f.type).typeArguments.get(0);
        if (isPojo(type.toString(), options)) {
          return CodeBlock.builder()
              .add("$L.$L($L.$L()",
                  pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name, "List"))
              .add(".stream()\n")
              .add(".map($T::convert)\n", type)
              .add(".collect($T.toImmutableList()))", ImmutableList.class)
              .build();
        }
        return CodeBlock.builder()
            .add("$L.$L($L.$L())",
                pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name, "List"))
            .build();
      } else {
        if (isPojo(f.type.toString(), options)) {
          return CodeBlock.builder()
              .add("$L.$L($T.convert($L.$L()))",
                  pojo, fieldToMethod("set", f.name), f.type, proto, fieldToMethod("get", f.name))
              .build();
        }
        return CodeBlock.builder()
            .add("$L.$L($L.$L())",
                pojo, fieldToMethod("set", f.name), proto, fieldToMethod("get", f.name))
            .build();
      }
    }).collect(Collectors.toList());

    MethodSpec.Builder builder = MethodSpec.methodBuilder("convert")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(pojoClass)
        .addParameter(protoClass, proto)
        .addStatement("$T $L = new $T()", pojoClass, pojo, pojoClass);
    for (CodeBlock codeBlock : codeBlocks) {
      builder.addStatement(codeBlock);
    }
    return builder
        .addStatement("return pojo")
        .build();
  }

  private static boolean isPojo(String name, Options options) {
    boolean isPojo = false;
    name = name.substring(name.lastIndexOf('.') + 1);
    if (!options.prefix().isEmpty()) {
      isPojo = name.startsWith(options.prefix());
    }
    if (!options.suffix().isEmpty()) {
      isPojo = name.endsWith(options.suffix());
    }
    return isPojo;
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
