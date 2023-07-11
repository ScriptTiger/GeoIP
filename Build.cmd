@echo off

echo Set build information for Java client...
set PROJECT=GeoIP
set PACKAGE=geoip
set ENTRY=Main

if exist Release (
	echo Cleaning up old release files...
	rd /s /q Release
)

set STATUS=complete

set JV=8
call :Build

set JV=17
call :Build

echo Build %STATUS%
exit /b

:Build
echo Compiling source files to Java %JV%...
if %JV% leq 8 (javac -d Release\%JV% src\%PACKAGE%\*.java --release %JV%
) else javac -d Release\%JV% src\%PACKAGE%\*.java src\module-info.java --release %JV%

if %ERRORLEVEL% neq 0 (
	echo Compile error
	set STATUS=incomplete
	exit /b
)

echo Archiving to Java %JV% jar file...
cd Release\%JV%
if %JV% leq 8 (jar cfe %PROJECT%-%JV%.jar %PACKAGE%.%ENTRY% %PACKAGE%\*.class
) else jar cfe %PROJECT%-%JV%.jar %PACKAGE%.%ENTRY% %PACKAGE%\*.class module-info.class
cd ..\..

exit /b