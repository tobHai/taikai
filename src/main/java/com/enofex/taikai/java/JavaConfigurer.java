package com.enofex.taikai.java;

import static com.enofex.taikai.internal.ArchConditions.notBePublicButNotStatic;
import static com.enofex.taikai.internal.DescribedPredicates.areFinal;
import static com.enofex.taikai.java.Deprecations.notUseDeprecatedAPIs;
import static com.enofex.taikai.java.HashCodeAndEquals.implementHashCodeAndEquals;
import static com.enofex.taikai.java.NoSystemOutOrErr.notUseSystemOutOrErr;
import static com.enofex.taikai.java.ProtectedMembers.notHaveProtectedMembers;
import static com.enofex.taikai.java.SerialVersionUID.beStaticFinalLong;
import static com.enofex.taikai.java.SerialVersionUID.namedSerialVersionUID;
import static com.enofex.taikai.java.UtilityClasses.havePrivateConstructor;
import static com.enofex.taikai.java.UtilityClasses.utilityClasses;
import static com.tngtech.archunit.lang.conditions.ArchConditions.beFinal;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.enofex.taikai.TaikaiRule;
import com.enofex.taikai.TaikaiRule.Configuration;
import com.enofex.taikai.configures.AbstractConfigurer;
import com.enofex.taikai.configures.ConfigurerContext;
import com.enofex.taikai.configures.Customizer;
import java.lang.annotation.Annotation;

public final class JavaConfigurer extends AbstractConfigurer {

  public JavaConfigurer(ConfigurerContext configurerContext) {
    super(configurerContext);
  }

  public JavaConfigurer imports(Customizer<ImportsConfigurer> customizer) {
    return customizer(customizer, () -> new ImportsConfigurer(configurerContext()));
  }

  public JavaConfigurer naming(Customizer<NamingConfigurer> customizer) {
    return customizer(customizer, () -> new NamingConfigurer(configurerContext()));
  }

  public JavaConfigurer utilityClassesShouldBeFinalAndHavePrivateConstructor() {
    return utilityClassesShouldBeFinalAndHavePrivateConstructor(null);
  }

  public JavaConfigurer utilityClassesShouldBeFinalAndHavePrivateConstructor(
      Configuration configuration) {
    return addRule(TaikaiRule.of(utilityClasses()
        .should(beFinal())
        .andShould(havePrivateConstructor()), configuration));
  }

  public JavaConfigurer methodsShouldNotDeclareGenericExceptions() {
    return methodsShouldNotDeclareGenericExceptions(null);
  }

  public JavaConfigurer methodsShouldNotDeclareGenericExceptions(Configuration configuration) {
    return addRule(TaikaiRule.of(methods()
        .should().notDeclareThrowableOfType(Exception.class)
        .orShould().notDeclareThrowableOfType(RuntimeException.class)
        .as("Methods should not declare generic Exception or RuntimeException"), configuration));
  }

  public JavaConfigurer noUsageOfDeprecatedAPIs() {
    return noUsageOfDeprecatedAPIs(null);
  }

  public JavaConfigurer noUsageOfDeprecatedAPIs(Configuration configuration) {
    return addRule(TaikaiRule.of(classes().should(notUseDeprecatedAPIs()), configuration));
  }

  public JavaConfigurer classesShouldResideInPackage(String regex, String packageIdentifier) {
    return classesShouldResideInPackage(regex, packageIdentifier, null);
  }

  public JavaConfigurer classesShouldResideInPackage(String regex, String packageIdentifier,
      Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .that().haveNameMatching(regex)
        .should().resideInAPackage(packageIdentifier), configuration));
  }

  public JavaConfigurer classesShouldResideOutsidePackage(String regex, String packageIdentifier) {
    return classesShouldResideOutsidePackage(regex, packageIdentifier, null);
  }

  public JavaConfigurer classesShouldResideOutsidePackage(String regex, String packageIdentifier,
      Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .that().haveNameMatching(regex)
        .should().resideOutsideOfPackage(packageIdentifier), configuration));
  }

  public JavaConfigurer classesShouldBeAnnotatedWith(String regex,
      Class<? extends Annotation> annotationType) {
    return classesShouldBeAnnotatedWith(regex, annotationType, null);
  }

  public JavaConfigurer classesShouldBeAnnotatedWith(String regex,
      Class<? extends Annotation> annotationType, Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .that().haveNameMatching(regex)
        .should().beMetaAnnotatedWith(annotationType)
        .as("Classes have name matching %s should be annotated with %s".formatted(
            regex, annotationType.getName())), configuration));
  }

  public JavaConfigurer classesShouldBeAnnotatedWith(String regex, String annotationType) {
    return classesShouldBeAnnotatedWith(regex, annotationType, null);
  }

  public JavaConfigurer classesShouldBeAnnotatedWith(String regex, String annotationType,
      Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .that().haveNameMatching(regex)
        .should().beMetaAnnotatedWith(annotationType)
        .as("Classes have name matching %s should be annotated with %s".formatted(regex,
            annotationType)), configuration));
  }

  public JavaConfigurer classesShouldImplementHashCodeAndEquals() {
    return classesShouldImplementHashCodeAndEquals(null);
  }

  public JavaConfigurer classesShouldImplementHashCodeAndEquals(Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .should(implementHashCodeAndEquals()), configuration));
  }

  public JavaConfigurer fieldsShouldNotBePublic() {
    return fieldsShouldNotBePublic(null);
  }

  public JavaConfigurer fieldsShouldNotBePublic(Configuration configuration) {
    return addRule(TaikaiRule.of(fields()
        .should(notBePublicButNotStatic()), configuration));
  }

  public JavaConfigurer noUsageOf(Class<?> clazz) {
    return noUsageOf(clazz, null);
  }

  public JavaConfigurer noUsageOf(String typeName) {
    return noUsageOf(typeName, null);
  }

  public JavaConfigurer noUsageOf(String typeName, Configuration configuration) {
    return addRule(TaikaiRule.of(noClasses()
        .should().dependOnClassesThat().areAssignableTo(typeName), configuration));
  }

  public JavaConfigurer noUsageOf(Class<?> clazz, Configuration configuration) {
    return addRule(TaikaiRule.of(noClasses()
        .should().dependOnClassesThat().areAssignableTo(clazz), configuration));
  }

  public JavaConfigurer noUsageOfSystemOutOrErr() {
    return noUsageOfSystemOutOrErr(null);
  }

  public JavaConfigurer noUsageOfSystemOutOrErr(Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .should(notUseSystemOutOrErr()), configuration));
  }

  public JavaConfigurer finalClassesShouldNotHaveProtectedMembers() {
    return finalClassesShouldNotHaveProtectedMembers(null);
  }

  public JavaConfigurer finalClassesShouldNotHaveProtectedMembers(Configuration configuration) {
    return addRule(TaikaiRule.of(classes()
        .that(areFinal())
        .should(notHaveProtectedMembers())
        .as("Final classes should not have protected members"), configuration));
  }

  public JavaConfigurer serialVersionUIDFieldsShouldBeStaticFinalLong() {
    return serialVersionUIDFieldsShouldBeStaticFinalLong(null);
  }

  public JavaConfigurer serialVersionUIDFieldsShouldBeStaticFinalLong(Configuration configuration) {
    return addRule(TaikaiRule.of(fields()
        .that(namedSerialVersionUID())
        .should(beStaticFinalLong())
        .as("serialVersionUID should be static final long"), configuration));
  }

  @Override
  public void disable() {
    disable(ImportsConfigurer.class);
    disable(NamingConfigurer.class);
  }
}