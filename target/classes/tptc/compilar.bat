@echo off
echo === COMPILANDO ANALIZADOR LEXICO ===

REM Verificar que existe la gramática
if not exist "compilador.g4" (
    echo Error: No se encuentra el archivo compilador.g4
    pause
    exit /b 1
)

REM Compilar el código Java generado por ANTLR
echo Compilando archivos Java...
javac -cp ".;antlr-4.13.1-complete.jar" *.java

if %ERRORLEVEL% neq 0 (
    echo Error en la compilación
    pause
    exit /b 1
)

echo.
echo ✓ Compilación exitosa
echo.
echo === INSTRUCCIONES DE USO ===
echo.
echo Para analizar un archivo:
echo   java -cp ".;antlr-4.13.1-complete.jar" tptc.AnalizadorLexico programa_prueba.txt
echo.
echo Para modo interactivo:
echo   java -cp ".;antlr-4.13.1-complete.jar" tptc.AnalizadorLexico
echo.
echo Archivos de prueba disponibles:
echo   - programa_prueba.txt (programa válido)
echo   - programa_errores.txt (programa con errores)
echo.

pause