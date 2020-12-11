![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

# XAIPValidator
The XAIPValidator is a tool designed for validation of an XML formatted Archival Information Package (XAIP). Using a modular structure and plugin mechanism, the validator is validating an XAIP by using those modules which can vary by their implementation.

Validation will be done step by step in following order:

1. Dispatcher loading modules
2. Calling SyntaxModule
3. Calling SignatureFinderModule
4. Calling SignatureVerificationModule
5. Calling ProtocolAssemblerModule

## Getting Started

```
git clone git@git.procilon.local:pronext/projektkomponenten/xaipvalidator.git

cd xaipvalidator

mvn clean package

# CLI
# Mac/Linux/Windows
java -jar xaip-validator-cli/target/xaip-validator-cli.jar -i ~/Dokumente/XAIP-Validator/validator/PAdES.xaip -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definitions


# SOAP Server
# Mac/Linux
java -cp "xaip-validator-soap/target/xaip-validator-soap-1.0.6-1.jar:target/dependency/*" de.bund.bsi.tresor.xaip.validator.soap.Server -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definition -Mverifier.wsdlUrl="https://host:port/VerificationService/S4?wsdl"

# Windows
java -cp "xaip-validator-soap/target/xaip-validator-soap-1.0.6-1.jar;target/dependency/*" de.bund.bsi.tresor.xaip.validator.soap.Server -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definition -Mverifier.wsdlUrl="https://host:port/VerificationService/S4?wsdl"
```

## Prerequisites
- Java 11

## Installing

```
git clone git@git.procilon.local:pronext/projektkomponenten/xaipvalidator.git

cd xaipvalidator

mvn clean package

java -jar xaip-validator-cli/target/xaip-validator-cli.jar -i ~/Dokumente/XAIP-Validator/validator/PAdES.xaip -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definition
```


## Components
The XAIPValidator consists of multiple modules.

![Components](overview_components.jpg "Components")

### CLI

**Description:** The cli version of the XAIPValidator is being used for validation of XAIP's via the command line.

**Usage:** `java -jar xaip-validator.jar [OPTION [ARG]*]*`

**Options:**

```
-M=<moduleConfig>
	Passing a single module configuration property to the validator. The property and requirements for any module configuration properties are defined by the implementing module.

    Example: -Mverifier.wsdlUrl=http://localhost:8080/s4?wsdl

    This example is using the property config verifier.wsdlUrl which is a required config property of the DefaultVerifierModule.

    Instead of passing multiple module properties as an argument, a config file which contains the module properties can be passed instead. For more informations see –c, --config <file>

-c, --config <file>
    Passing a configuration file in form of a property file to the validator. This file can contain any module configuration property. The configuration file is being passed to every module so they can scan the content and retrieve any configuration properties they define. 

    The handling is just the same as passing all configuration properties via a separate command line argument.

    This argument can also be used together with single command line module property arguments.

    Example: -c config.properties

    Content of config.properties:
      validator.schemaDir=/tmp/xaip/definition
      verifier.wsdlUrl=http://localhost:8080/s4?wsdl

-i, --in, --input <file>
    Passing a <file> as a source for the xaip validation. Omitting this argument will take the standard <inpuStream> for the validation.

    Example: -i /tmp/sample.xaip
	
-o, --out, --output <file>
    Defining a definition for the validation result. Omitting this argument will write the result to the standard <outputStream> instead.

    Example: -o /tmp/report.xml

-v, --verify
    This flags enables the signature verification which is being executed by the [SignatureVerifierModule]. Omitting this flag will only execute the syntax validation.
-d, --debug
    Flag to enable debug output for a better analysis of the validator behaviour. This output can contain stacktraces or other kinds of errors even when everything works fine.

-l, --log <file>
    Since this tool is not only creating a report but also log output this argument can be used to separate the log output of the validator into a dedicated document.

    Example: -l validator.log

-h, --help
	Printing the usage of the validator.
```

**Important Notes**
- when using the command line version of the XAIPValidator you **must** specify the location of the schema files by using the parameter `-Mvalidator.schemaDir` unless you are using a custom syntax validation module. Otherwise the validator will not start
- when using the parameter `-o` you must specify a directory. Currently the validator cannot write a plain file located in the same directory
- when using parameter `-v` you need to specify the URL of the signature verification service to be used unless you are using a custom verification module. The signature verification service must be compliant to the TR-03112 / OASIS interface definition (using VerifiyRequest)

### Server

**Description:** The server version of the XAIPValidator is being used to provide a soap service implementing the verify function of the eCard api. This api can be used to send a verifyRequest containing an XAIP which will be validated in the following steps.

**Usage:** `java -cp "target/xaip-validator-soap-1.0.1-2.jar:target/dependency/*" de.bund.bsi.tresor.xaip.validator.soap.Server [OPTION [ARG]*]*`

**Options:**

```
-M <moduleConfig>
	Passing a configuration for a module. This Option is optional since the need for a configuation is defined by the specific module implementation.
	Providing a configuration can be done by either directly setting a specific configuration or passing a complete configuration file. In both cases the arguments have to match a specific pattern to identify the module  the configuration applies to:
    
    $MODULE.$PROPERTY
	
	Modules : finder,verifier,validator,assembler
	Property: either the keyword `conf` which is reserved to load the configuration from a file followed by this property or any configuration keyword the specific module implementation offers

	Examples: 
		-Mverifier.conf=/tmp/example.properties
		-Mverifier.wsdlUrl=http://localhost:8080/s4?wsdl

-p, --port <port>
	Port the server should be published to, 8080 by default

-P, --protocol <protocol>
	Protocol to be used, `http` by default

-H, --host <hostname>
	Hostname the server is published to

--path <path>
	Custom path the service should be used, `/xaip-validate` by default

-d, --debug, --verbose
	Flag to enable verbose logging over all components. The additional logs will be written to the same output as the other log entries.

-h, --help
	Printing the manual of the validator
```

## Modules

### Default Validator

**Description:** Validating the XAIP using the provided schema files (XSD's)

**Configurations:**

| ConfigName            | Example          | Description                                       |
|-----------------------|------------------|---------------------------------------------------|
| *validator.schemaDir  | /tmp/xaip-schema | schema directory containing all xaip schema files |

\* - required configuration

### Default Verifier

**Description:** Sending the found signature objects to a configured verification service and retrieving the response

**Configurations:**

| ConfigName        | Example                                                   | Description                          |
|-------------------|-----------------------------------------------------------|--------------------------------------|
| *verifier.wsdlUrl | https://host:port/VerificationService/S4?wsdl             | url of the verification service wsdl |
|  verifier.user    | umsysadmin                                                | uid for the token creation           |
|  verifier.pass    | someSecret                                                | password of the user                 |
|  verifier.umUrl   | https://protectr.procilon.test/UserManager/v1/login       | loginUrl of the procilon userManager |
|  verifier.idpUrl  | https://protectr.procilon.test/idp/profile/SAML2/SOAP/ECP | idpUrl of the procilon idp           |

\* - required configuration

## Known Limitations

The following limitations apply:

- The XAIPValidator has a strong dependency to the external signature validation service. The current version of the service is not able to validate XML signatures properly. Therefore the validation of XAIPs containing XML signatures will lead to validation reports with unsupported signature formats.
- The processing of XAdES-compliant signature objects is currently not fully supported. This issue will be fixed in one of the next SNAPSHOT releases
- Information for `TransformInfoType` is currently not generated due to inconsistencies in the verification report scheme
- Extensions are not evaluated due to their dependency to specific profiles
- The content of Metadata sections is not evaluated with the exception of their well-formedness

**Be aware of the following issue**

When running the validator, a warning is generated due to an illegal access to the ClassLoader. As far as known, this warning does not influence the correct execution of the program and originates in the jaxb library which is being used.

## Test Environment ##

- Use the following URL for the validation service: https://staging.protectr.de/VerificationService/S4?wsdl
- Testmaterial is supplied separately together with sample XAIP-files and a shell script for test execution

