![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

# XAIPValidator
The XAIPValidator is a tool designed for the validation of an XML formatted Archival Information Package (XAIP). Using a modular structure and plugin mechanism, the validator is validating the XAIP by using those modules which can vary by their implementation.

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
java -jar xaip-validator-cli/target/xaip-validator-cli.jar -i ~/Dokumente/XAIP-Validator/validator/PAdES.xaip -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definition

# SOAP Server
java -cp "target/xaip-validator-soap-0.0.1-SNAPSHOT.jar:target/dependency/*" de.bund.bsi.tresor.xaip.validator.soap.Server -Mvalidator.schemaDir=default-syntax-validator/src/main/resources/definition -Mverifier.wsdlUrl="https://host/VerificationService/S4?wsdl"
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
-M <moduleConfig>
	Passing a configuration for a module. This Option is optional since the need for a configuation is defined by the specific module implementation.
	Providing a configuration can be done by either directly setting a specific configuration or passing a complete configuration file. In both cases the arguments have to match a specific pattern to identify the module  the configuration applies to:
    
	$MODULE.$PROPERTY
	
	Modules : finder,verifier,validator,assembler
	Property: either the keyword `conf` which is reserved to load the configuration from a file followed by this property or any configuration keyword the specific module implementation offers

	Examples: 
		-Mverifier.conf=/tmp/example.properties
		-Mverifier.wsdlUrl=http://localhost:8080/s4?wsdl

-i, --in, --input <file>
	Source of the XAIP to validate. This can be an external <file> or the standard inputstream when omitting this option.
	Example: -i /tmp/sample.xaip
	
-o, --out, --output <file>
	Destination of the validation result. The <file> argument can be a directory or a file. If the destination does not exist it will be created. In case the destination directory/file exists it needs to be writeable. Omitting this option will print the result to the standard output.
	Example: -o /tmp/results/result_aoid_12345.xml
	
-v, --verify
	This flag is being used to trigger the signature verification in addition to the syntax validation which is always enabled by default.

-d, --debug, --verbose
	Flag to enable verbose logging over all components. The additional logs will be written to the same output as the other log entries.

-l, --log <file>
	Output destination for the log entries which will be generated by the validator and covers all modules unless they don't use the ModuleLogger. The default output for the log entries is standard error.
	Example: -l /tmp/validate.log

-h, --help
	Printing the manual of the validator
```

### SERVER

**Description:** The server version of the XAIPValidator is being used to provide a soap service implementing the verify function of the eCard api. This api can be used to send a verifyRequest containing an XAIP which will be validated in the following steps.

**Usage:** `java -jar xaip-validator-soap.jar [OPTION [ARG]*]*`

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