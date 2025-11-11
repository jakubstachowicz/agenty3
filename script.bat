@echo off
setlocal enabledelayedexpansion

if "%~1"=="" (
  echo Usage: %~nx0 ^<number_of_containers^> [offset]
  echo Example: %~nx0 3 10
  exit /b
)

set COUNT=%~1
set OFFSET=%~2
if "%OFFSET%"=="" set OFFSET=0

for /L %%i in (1,1,%COUNT%) do (
  set /A num=%%i + %OFFSET%
  set /A localPort=5700 + !num!
  start "" java -cp "lib\jade.jar" jade.Boot -host localhost -port 5698 -local-host localhost -local-port !localPort! -name PLATFORMAJADE -container -container-name CONTAINER!num!
)

endlocal
