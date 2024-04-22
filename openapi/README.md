# api
Contains an openapi specification for the trireg2 application.

## Module structure
* The root yaml source file is [src/](src/trireg2.yaml). We split the definitions into multiple files to prevent this file from becoming too large and hard to maintain.
* The yaml sources are processed using the [openapi-generator plugin](https://openapi-generator.tech/docs/plugins/#gradle), which generates:
  * One big yaml file with the complete specification, which can be [published](#publishing-the-openapi-specification)
  * A html document with the complete specification

## Publishing the openapi specification
Note that the following description doesn't work out of the box in a development environment, because it requires setting the environment variables `GITHUB_USERNAME` and `GITHUB_TOKEN`. It is intended to be used in a [github-workflow](../.github/workflows/api-publish.yml).

If you have changed the openapi specification, you must bump the version in the `publishing` block of the [build file](build.gradle.kts) before publishing. This will automatically be reflected in the version of the generated file.

The specification can be published as a zip distribution using the following command from the root of the project:
```
gradle openapi:publish
```  