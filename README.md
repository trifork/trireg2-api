# TriReg2 OpenAPI specification

A versioned [OpenAPI v3](https://spec.openapis.org/oas/v3.1.0) specification for the TriReg2 API. Kept in a separate repository to allow public access, and for
clients to request changes or suggestions via pull requests or adding issues to the issue tracker.

The OpenAPI specification contains a user API for the new TriReg2 service.

Later, it will also contain an admin API and import APIs towards the TriReg2 service.

## Publish a new API
The TriReg2 OpenAPI specification is published using a [GitHub action](https://github.com/trifork/trireg2-api/actions/workflows/api-publish.yml) which is triggered when creating a new release. 

### Create a new release
Navigate to the `Code` tab and find the `Releases` section, and press the `Draft a new release` button. Create a new tag, for example v0.0.7, and add a title for your release, for example `Release v0.0.7`.

Check the check-box `This is a pre-release`, if this is a pre-release version, and then press the `Publish release` button.

### Versioning

TODO

## Download the API

Download the API as a zip file from Github assets, which can be found here... TODO

## Maven and Gradle artifacts

TODO 

## Git Submodule dependency

You can also add a dependency to this API using this repo as a [git submodule](https://git-scm.com/book/en/v2/Git-Tools-Submodules) in your application.

If you start from scratch and your application does not have this git submodule dependency yet, add it as a submodule with the command (in your application root folder):

```
git submodule add git@github.com:trifork/trireg2-api.git trireg2-api
```

If you have cloned a repository, that already has a dependency to this submodule, you need to initialize and update the `trireg2-api` submodule with the command (in your application root folder):

```
git submodule update --init --recursive
```

During development phase, if there are new changes to this API, update your submodule from remote with the command (in your application root folder):

```
git submodule update --recursive
```

### Deploy keys and secrets

Since this repository is a private Github repository, we need to grant access for other repos to access the code, for example when checking out the submodule in a Github workflow action. 

Therefore you need to generate a public/private key pair and use the name of the private key in the repository, when checking out the submodule in the Github workflow.

### Step 1: 

Generate an RSA public/private key pair from your command line using the command:

```
ssh-keygen -t rsa -b 4096
```

and save the key in your `.ssh` folder and name the key with the name of the repository that needs access to the submodule, for example name the key `id_rsa_trireg2-app`. 

### Step 2: 

Navigate to the repository that needs access, for example your `trireg2-app` repo, and go to the `Settings` tab, go to `Secrets`, and choose `Actions`.

Press the button `New repository key` and name the key `SUBMODULE_CONTENT_PULL_KEY`. Copy the private part of the key generated in step 1 and paste the content of the key to the section named `Secret`. Press save. 

### Step 3: 

Navigate to the Settings tab of this API repo, and go to Deploy keys.  

https://github.com/trifork/trireg2-api/settings/keys

Press the button `Add deploy key` and give the key a title like "Submodule access for Github Action under application repo".
Copy the public part of the key pair generated in step 1 (i.e. the content in the `*.pub` file) and paste the key into the Key section. And press the `Add key` button.

### Step 4:

In the Github workflow file for your application repository that needs access to this submodule, for example in this case the `trireg2-app` repo, the ssh-key is mentioned as `secrets.SUBMODULE_CONTENT_PULL_KEY` in the step where the submodule is checked out and build together with the source code.
