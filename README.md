# CoverageQuickLink Guidelines

This document provides essential information for developers working on the CoverageQuickLink IntelliJ IDEA plugin.

## Build/Configuration Instructions

### Prerequisites
- JDK 17 or later
- Gradle 8.0+ (or use the included Gradle wrapper)
- IntelliJ IDEA (2023.3+)

### Building the Plugin

1. **Using Gradle Wrapper**:
   ```bash
   # Windows
   gradlew.bat build
   
   # Unix/Linux/macOS
   ./gradlew build
   ```

2. **Running in Development Mode**:
   ```bash
   # Windows
   gradlew.bat runIde
   
   # Unix/Linux/macOS
   ./gradlew runIde
   ```

3. **Building Plugin Distribution**:
   ```bash
   # Windows
   gradlew.bat buildPlugin
   
   # Unix/Linux/macOS
   ./gradlew buildPlugin
   ```
   The plugin ZIP file will be generated in `build/distributions/`.

### Configuration Properties

The project uses the following Gradle properties (defined in `gradle.properties`):
- `platformType=IU` - Targets IntelliJ IDEA Ultimate
- `platformVersion=2023.3` - Targets IntelliJ IDEA version 2023.3

To target a different IntelliJ IDEA version, modify these properties.

## Testing Information

### Test Structure

Tests should be placed in the `src/test/java` directory, mirroring the package structure of the main source code.

### Running Tests

1. **Run All Tests**:
   ```bash
   # Windows
   gradlew.bat test
   
   # Unix/Linux/macOS
   ./gradlew test
   ```

2. **Run Specific Test**:
   ```bash
   # Windows
   gradlew.bat test --tests "org.stark.coveragequicklink.OpenInCoverageReportActionTest"
   
   # Unix/Linux/macOS
   ./gradlew test --tests "org.stark.coveragequicklink.OpenInCoverageReportActionTest"
   ```

### Adding New Tests

1. Create a new test class in the appropriate package under `src/test/java`.
2. For simple tests, use JUnit 4 (already included as a dependency).
3. For tests that require IntelliJ platform APIs, use the IntelliJ Platform Test Framework.

#### Example Test

```java
package org.stark.coveragequicklink;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleTest {
    @Test
    public void testSomething() {
        // Test implementation
        assertTrue(true);
    }
}
```

### Testing IntelliJ Platform Components

For testing components that interact with the IntelliJ platform:

1. Add the following dependencies to `build.gradle.kts`:
   ```kotlin
   testImplementation("org.mockito:mockito-core:4.8.0")
   testImplementation("com.intellij.platform:test-framework:233.0")
   ```

2. Create a light test that extends `LightJavaCodeInsightFixtureTestCase`:
   ```java
   public class MyLightTest extends LightJavaCodeInsightFixtureTestCase {
       @Override
       protected void setUp() throws Exception {
           super.setUp();
           // Test setup
       }
       
       public void testFeature() {
           // Test implementation using myFixture
       }
   }
   ```

## Additional Development Information

### Code Style

- Follow IntelliJ IDEA's Java code style.
- Use meaningful variable and method names.
- Add JavaDoc comments for public methods and classes.

### Plugin Structure

- `OpenInCoverageReportAction.java` - Main action class that handles opening coverage reports.
- `plugin.xml` - Plugin configuration file that defines the plugin's metadata and extension points.

### Supported Coverage Formats

The plugin currently supports the following coverage formats:
- IntelliJ IDEA coverage files (`.ic`)
- JaCoCo coverage files (`.exec`, `.xml`)

To add support for additional formats, update the `EXTENSION_TO_RUNNER` map in `OpenInCoverageReportAction.java`.

### Debugging

1. Run the plugin in debug mode:
   ```bash
   # Windows
   gradlew.bat runIde --debug-jvm
   
   # Unix/Linux/macOS
   ./gradlew runIde --debug-jvm
   ```

2. Connect your IDE's debugger to port 5005.

### Publishing

To prepare the plugin for publishing to the JetBrains Marketplace:

1. Update the plugin version in `build.gradle.kts`.
2. Update the change notes in `plugin.xml`.
3. Build the plugin distribution with `gradlew buildPlugin`.
4. Upload the ZIP file from `build/distributions/` to the JetBrains Marketplace.