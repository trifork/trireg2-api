import org.apache.tools.ant.filters.ReplaceTokens
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("org.openapi.generator") version "7.4.0"
    `maven-publish`
}

group = "com.trifork"
version = "0.0.4"

val openAPIInputDir = "$projectDir/src/"
val openAPIInputYaml = "$openAPIInputDir/trireg2.yaml"
val outputGenerationRootDir: Provider<Directory> = layout.buildDirectory.dir("generated")
val outputFileBaseName = "trireg2-user-openapi-v$version"
val htmlFilename = "$outputFileBaseName.html"
val yamlFilename = "$outputFileBaseName.yaml"

val generateOpenAPIYamlTask = task(name = "generateOpenAPIYaml", type = GenerateTask::class) {
    inputs.dir(openAPIInputDir)
    generatorName = "openapi-yaml"
    inputSpec = openAPIInputYaml
    outputDir = outputGenerationRootDir.map { it.dir("yaml").asFile.path }
    //Available options: https://openapi-generator.tech/docs/generators/openapi-yaml
    configOptions = mapOf(
        "outputFile" to yamlFilename,
    )
}

val generateOpenAPIHTMLDocTask = task(name = "generateOpenAPIHTMLDoc", type = GenerateTask::class) {
    dependsOn(generateOpenAPIYamlTask)
    inputs.dir(generateOpenAPIYamlTask.outputDir)
    generatorName = "html2"
    inputSpec = generateOpenAPIYamlTask.outputDir.map { "$it/$yamlFilename" }
    outputDir = outputGenerationRootDir.map { it.dir("html").asFile.path }
    //Available options: https://openapi-generator.tech/docs/generators/html2
    configOptions = mapOf()
}

val generateVersionPropertiesTask = task(name = "generateVersionProperties", type = WriteProperties::class) {
    destinationFile = file(outputGenerationRootDir.map { it.file("version.properties") })
    property("version", version)
    property("htmlFile", htmlFilename)
    property("yamlFile", yamlFilename)
}
val assembleOpenAPIZipDistTask = task(name = "assembleOpenAPIZipDist", type = Zip::class) {
    dependsOn(generateOpenAPIYamlTask, generateOpenAPIHTMLDocTask, generateVersionPropertiesTask)
    from(generateOpenAPIYamlTask.outputDir)
    from(generateOpenAPIHTMLDocTask.outputDir) {
        include("index.html")
        rename("index.html", htmlFilename)
    }
    from(openAPIInputDir) {
        include("**/*.yaml")
        into("separated")
    }
    from(generateVersionPropertiesTask.outputs.files.singleFile)
    exclude("**/.openapi-generator", "**/.openapi-generator-ignore", "**/README.md")
    filter(ReplaceTokens::class, "tokens" to mapOf("VERSION" to version))
    archiveBaseName.set("trireg2-openapi")
    destinationDirectory.set(layout.buildDirectory.dir("distributions").get())
}

val cleanTask = task(name = "clean", type = Delete::class) {
    delete(layout.buildDirectory)
}

tasks.publish {
    dependsOn(cleanTask, assembleOpenAPIZipDistTask)
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Trifork/trireg2-api")
            credentials {
                username = System.getenv("GITHUB_USERNAME") ?: "GITHUB_USERNAME missing"
                password = System.getenv("GITHUB_TOKEN") ?: "GITHUB_TOKEN missing"
            }
        }
    }
    publications {
        create<MavenPublication>("openapi") {
            artifactId = "trireg2-api"
            artifact(assembleOpenAPIZipDistTask)
        }
    }
}
