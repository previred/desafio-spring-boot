package com.dio.task_manager;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

class ArchitectureTest {
    @Test
    void noCyclicDependenciesBetweenPackages() {
        JavaClasses classes = new ClassFileImporter()
                .importPackages("com.dio.task_manager");

        SlicesRuleDefinition.slices()
                .matching("com.dio.task_manager.(**)..")
                .should().beFreeOfCycles()
                .check(classes);
    }
}
