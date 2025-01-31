Systemon output and Windows log parser. It sorts the log file out by event ID and makes the events readable. All you have to do is edit the source and destination paths in main and you are good to go.

If you are trying to parse your Sysmon output read below. Windows logs should work auomatically.

Read how to setup Sysmon with CAPE here https://parkwork5.github.io/2024/08/02/Sysmon&CAPEv2.html.

You will need to run iconv -f UTF-8 -t UTF-8 -c sysmon.xml -o formated.xml because CAPE(or Sysmon? Idk) does not format the output correctly and it needs to be fixed. Code will not run without this.
