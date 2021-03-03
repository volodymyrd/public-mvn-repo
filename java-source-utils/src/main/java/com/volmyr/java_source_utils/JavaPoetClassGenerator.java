package com.volmyr.java_source_utils;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import javax.lang.model.element.Modifier;

/**
 * Java class generator using JavaPoet library.
 */
public final class JavaPoetClassGenerator {

  private final String packageName;
  private final boolean skipJavaLangImports;

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
    TypeSpec helloWorld = TypeSpec.classBuilder(className)
        .addModifiers(modifiers)
        .addMethods(methods)
        .build();

    return JavaFile.builder(packageName, helloWorld)
        .skipJavaLangImports(skipJavaLangImports)
        .build()
        .toString();
  }
}
