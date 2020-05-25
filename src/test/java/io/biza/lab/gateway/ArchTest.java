package io.biza.lab.gateway;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.biza.lab.gateway");

        noClasses()
            .that()
                .resideInAnyPackage("io.biza.lab.gateway.service..")
            .or()
                .resideInAnyPackage("io.biza.lab.gateway.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..io.biza.lab.gateway.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
