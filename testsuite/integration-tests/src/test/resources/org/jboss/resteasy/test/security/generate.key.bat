set PASSWORD="123456"
set DN_SERVER="CN=server"
set DN_CLIENT="CN=client"
set DN_UNTRUSTED="CN=untrusted"
set VALIDITY=7300
set HOST=%~1
set DIR=%~2

rem clean up
cd %DIR%
del /Q /F client.* server.* client2.* server2.* untrusted.* 2>nul

>output.txt (
  call :create_keystore "server", %DN_SERVER%, %PASSWORD%, %HOST%
  call :create_keystore "server2", %DN_SERVER%, %PASSWORD%, "199.111.68.7"
  call :create_keystore "client", %DN_CLIENT%, %PASSWORD%, %HOST%
  call :create_keystore "untrusted", %DN_UNTRUSTED%, %PASSWORD%

  call :export_cert "server.keystore", %DN_SERVER%, "server.crt", %PASSWORD%
  call :export_cert "server2.keystore", %DN_SERVER%, "server2.crt", %PASSWORD%
  call :export_cert "client.keystore", %DN_CLIENT%, "client.crt", %PASSWORD%
  call :export_cert "untrusted.keystore", %DN_UNTRUSTED%, "untrusted.crt", %PASSWORD%

  call :import_cert "server.truststore", %DN_CLIENT%, "client.crt", %PASSWORD%
  call :import_cert "client.truststore", %DN_SERVER%, "server.crt", %PASSWORD%
  call :import_cert "client2.truststore", %DN_SERVER%, "server2.crt", %PASSWORD%
)
del /Q /F output.txt 2>nul
del /Q /F output2.txt 2>nul
EXIT /B %ERRORLEVEL%

:create_keystore
  set KEY_FILE=%~1
  set DN=%~2
  set PASS=%~3
  set HOST=%~4

  if [%HOST%] == [] (
    set HOST="127.0.0.1"
  )
  keytool -genkey ^
        -keyalg RSA ^
        -alias %DN% ^
        -keystore "%KEY_FILE%.keystore"^
        -storepass %PASS% ^
        -validity %VALIDITY% ^
        -keysize 2048 ^
        -keypass %PASS% ^
        -dname "CN=Tomas, OU=Tomas, O=Tomas, L=Brno, ST=Czech Republic, C=CZ" ^
        -ext "SAN=dns:localhost,ip:%HOST%" >>output2.txt
  rem export as PKCS12
  keytool -importkeystore -srckeystore "%KEY_FILE%.keystore" ^
    -destkeystore "%KEY_FILE%.p12" -deststoretype PKCS12 ^
    -srcstorepass %PASS% -deststorepass %PASS% >>output2.txt
EXIT /B 0

:export_cert
  set KEY_FILE=%~1
  set ALIAS=%~2
  set EXPORT_FILE=%~3
  set PASS=%~4
  keytool -export -alias %ALIAS% -keystore %KEY_FILE% ^
     -storepass %PASS% -file %EXPORT_FILE% >>output2.txt
EXIT /B 0

:import_cert
  set KEY_FILE=%~1
  set ALIAS=%~2
  set IMPORT_FILE=%~3
  set PASS=%~4
  keytool -import -noprompt -alias %ALIAS% -keystore %KEY_FILE% ^
     -storepass %PASS% -file %IMPORT_FILE% >>output2.txt
EXIT /B 0
