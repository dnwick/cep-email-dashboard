Read me file for running UI tests

In order to run UI tests testing environment should meet following prerequisites.

1. WSO2 CEP 3.1.0 instance runnig without a port off set (i.e. on port 9443)
2. WSO2 ESB 4.8.1 instance running with port off set 1 (i.e. on port 9444)
3. follow installation instructions for email monitor to prepare CEP and ESB instances.
4. firefox version 22.0 - 24.0 installed on mechine or downloaded and extracted to a seperate location.

When both CEP and ESB instances are up and runnig, go to UI test directory and use following terminal commands to run UI tests.

1. If machine has firefox version 22.0 - 24.0 installed, use "mvn clean install" to run UI tests
2. If machine has a firefox version other than 22.0 - 24.0, download and extract firefox 22.0 - 24.0 installation files to a directory,
and use "mvn clean install -Dwebdriver.firefox.bin=<Path_to_extracted_directory>/firefox-bin"
