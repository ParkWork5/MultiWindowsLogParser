Converts CAPEV2 Sysmon output into human readable format.

Read how to setup Sysmon with CAPE here https://parkwork5.github.io/2024/08/02/Sysmon&CAPEv2.html.

You will need to run iconv -f UTF-8 -t UTF-8 -c sysmon.xml -o formated.xml because CAPE does not format the Sysmon output correctly and it needs to be fixed. Code will not run without this.
