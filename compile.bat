@echo off
REM Compile script for Java 2D Game

REM Clean old class files
del /Q *.class 2>nul

REM Compile all source files
javac -d . -sourcepath src src\App.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
    exit /b 1
)

