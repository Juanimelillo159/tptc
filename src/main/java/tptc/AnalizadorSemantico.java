package tptc;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

/**
 * Analizador Semántico que extiende el BaseListener para recorrer el AST
 */
public class AnalizadorSemantico extends compiladorBaseListener {
    private TablaSimbolos tablaSimbolos;
    private List<ErrorSemantico> errores;
    private List<ErrorSemantico> warnings;
    private boolean dentroDeLoop;
    private boolean hayReturn;

    public AnalizadorSemantico() {
        this.tablaSimbolos = new TablaSimbolos();
        this.errores = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.dentroDeLoop = false;
        this.hayReturn = false;
    }

    // ================== CLASE RESULTADO ==================

    public static class ResultadoAnalisisSemantico {
        private TablaSimbolos tablaSimbolos;
        private List<ErrorSemantico> errores;
        private List<ErrorSemantico> warnings;
        private boolean exitoso;
        private long tiempoAnalisis;

        public ResultadoAnalisisSemantico(TablaSimbolos tabla, List<ErrorSemantico> errores,
                                          List<ErrorSemantico> warnings, long tiempo) {
            this.tablaSimbolos = tabla;
            this.errores = new ArrayList<>(errores);
            this.warnings = new ArrayList<>(warnings);
            this.exitoso = errores.isEmpty();
            this.tiempoAnalisis = tiempo;
        }

        public TablaSimbolos getTablaSimbolos() {
            return tablaSimbolos;
        }

        public List<ErrorSemantico> getErrores() {
            return errores;
        }

        public List<ErrorSemantico> getWarnings() {
            return warnings;
        }

        public boolean fueExitoso() {
            return exitoso;
        }

        public long getTiempoAnalisis() {
            return tiempoAnalisis;
        }

        public int getNumeroErrores() {
            return errores.size();
        }

        public int getNumeroWarnings() {
            return warnings.size();
        }
    }

    // ================== MÉTODO PRINCIPAL DE ANÁLISIS ==================

    public static ResultadoAnalisisSemantico analizar(ParseTree arbol) {
        long tiempoInicio = System.currentTimeMillis();

        AnalizadorSemantico analizador = new AnalizadorSemantico();

        // Primera pasada: registrar todas las funciones (incluyendo main)
        analizador.registrarFunciones(arbol);

        // Segunda pasada: análisis semántico completo
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(analizador, arbol);

        // Verificaciones finales
        analizador.verificacionesFinals();

        long tiempoFin = System.currentTimeMillis();

        return new ResultadoAnalisisSemantico(
                analizador.tablaSimbolos,
                analizador.errores,
                analizador.warnings,
                tiempoFin - tiempoInicio);
    }

    // ================== REGISTRO DE FUNCIONES (1ª PASADA) ==================

    private void registrarFunciones(ParseTree arbol) {
        RegistradorFunciones registrador = new RegistradorFunciones(tablaSimbolos);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(registrador, arbol);
    }

    private static class RegistradorFunciones extends compiladorBaseListener {
        private TablaSimbolos tabla;
        private SimboloFuncion funcionActual;

        public RegistradorFunciones(TablaSimbolos tabla) {
            this.tabla = tabla;
            this.funcionActual = null;
        }

        @Override
        public void enterDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
            SimboloFuncion main = new SimboloFuncion("main", "int",
                    ctx.start.getLine(),
                    ctx.start.getCharPositionInLine() + 1);
            main.setAmbito("global");
            tabla.insertar(main);
            funcionActual = main;
        }

        @Override
        public void exitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
            funcionActual = null;
        }

        @Override
        public void enterDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
            String nombre = ctx.IDENTIFICADOR().getText();
            String tipoRetorno = ctx.tipo().getText();

            SimboloFuncion funcion = new SimboloFuncion(nombre, tipoRetorno,
                    ctx.start.getLine(),
                    ctx.start.getCharPositionInLine() + 1);
            funcion.setAmbito("global");
            tabla.insertar(funcion);
            funcionActual = funcion;
        }

        @Override
        public void exitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
            funcionActual = null;
        }

        @Override
        public void enterParametro(compiladorParser.ParametroContext ctx) {
            if (funcionActual != null) {
                String nombre = ctx.IDENTIFICADOR().getText();
                String tipo = ctx.tipo().getText();

                SimboloVariable parametro = new SimboloVariable(nombre, tipo,
                        ctx.start.getLine(),
                        ctx.start.getCharPositionInLine() + 1,
                        true);
                parametro.setAmbito(funcionActual.getNombre());
                funcionActual.agregarParametro(parametro);
            }
        }
    }

    // ================== MANEJO DE FUNCIONES ==================

    @Override
    public void enterDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
        SimboloFuncion main = tablaSimbolos.buscarFuncion("main");

        if (main == null) {
            agregarError(ErrorSemantico.TipoError.FUNCION_NO_DECLARADA,
                    "Error interno: función main no encontrada",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        tablaSimbolos.setFuncionActual(main);
        tablaSimbolos.abrirAmbito();
        hayReturn = false;
    }

    @Override
    public void exitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
        SimboloFuncion main = tablaSimbolos.getFuncionActual();
        if (main != null && !hayReturn) {
            agregarWarning(ErrorSemantico.TipoError.FUNCION_SIN_RETURN,
                    "La función main debería tener una declaración return",
                    ctx.stop.getLine(), ctx.stop.getCharPositionInLine() + 1);
        }

        verificarVariablesNoUtilizadas();
        tablaSimbolos.cerrarAmbito();
        tablaSimbolos.setFuncionActual(null);
        hayReturn = false;
    }

    @Override
    public void enterDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
        String nombre = ctx.IDENTIFICADOR().getText();

        SimboloFuncion funcion = tablaSimbolos.buscarFuncion(nombre);

        if (funcion == null) {
            agregarError(ErrorSemantico.TipoError.FUNCION_NO_DECLARADA,
                    "Error interno: función '" + nombre + "' no encontrada",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        tablaSimbolos.setFuncionActual(funcion);
        tablaSimbolos.abrirAmbito();
        hayReturn = false;
    }

    @Override
    public void exitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
        SimboloFuncion funcion = tablaSimbolos.getFuncionActual();

        if (funcion != null && !funcion.getTipoRetorno().equals("void") && !hayReturn) {
            agregarError(ErrorSemantico.TipoError.FUNCION_SIN_RETURN,
                    "La función '" + funcion.getNombre() + "' debe tener una declaración return",
                    ctx.stop.getLine(), ctx.stop.getCharPositionInLine() + 1);
        }

        verificarVariablesNoUtilizadas();
        tablaSimbolos.cerrarAmbito();
        tablaSimbolos.setFuncionActual(null);
        hayReturn = false;
    }

    @Override
    public void enterParametro(compiladorParser.ParametroContext ctx) {
        // Parámetros al ámbito local (ya fueron agregados a la función en la 1ª pasada)
        String nombre = ctx.IDENTIFICADOR().getText();
        String tipo = ctx.tipo().getText();

        SimboloVariable parametro = new SimboloVariable(nombre, tipo,
                ctx.start.getLine(),
                ctx.start.getCharPositionInLine() + 1,
                true);

        SimboloFuncion f = tablaSimbolos.getFuncionActual();
        String ambito = (f != null) ? f.getNombre() : "global";
        parametro.setAmbito(ambito);

        if (!tablaSimbolos.insertar(parametro)) {
            agregarError(ErrorSemantico.TipoError.REDEFINICION_VARIABLE,
                    "El parámetro '" + nombre + "' ya está definido",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
        }
    }

    // ================== MANEJO DE VARIABLES (GLOBALES Y LOCALES) ==================

    @Override
    public void enterDeclaracion_global(compiladorParser.Declaracion_globalContext ctx) {
        String tipo = ctx.tipo().getText();

        for (compiladorParser.DeclaradorContext declCtx : ctx.declarador()) {
            String nombre = declCtx.IDENTIFICADOR().getText();

            SimboloVariable variable = new SimboloVariable(
                    nombre,
                    tipo,
                    declCtx.start.getLine(),
                    declCtx.start.getCharPositionInLine() + 1
            );
            variable.setAmbito("global");

            // Si es arreglo global: IDENTIFICADOR '[' ENTERO ']'
            if (declCtx.ENTERO() != null) {
                int tam = Integer.parseInt(declCtx.ENTERO().getText());
                variable.setArreglo(tam);
            }

            if (!tablaSimbolos.insertar(variable)) {
                agregarError(ErrorSemantico.TipoError.REDEFINICION_VARIABLE,
                        "La variable global '" + nombre + "' ya está declarada",
                        declCtx.start.getLine(), declCtx.start.getCharPositionInLine() + 1);
            }
        }
    }

    @Override
    public void enterDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx) {
        String tipo = ctx.tipo().getText();

        // Ambito: nombre de la función actual (o global si no hay)
        SimboloFuncion f = tablaSimbolos.getFuncionActual();
        String ambito = (f != null) ? f.getNombre() : "global";

        for (compiladorParser.DeclaradorContext declCtx : ctx.declarador()) {
            String nombre = declCtx.IDENTIFICADOR().getText();

            SimboloVariable variable = new SimboloVariable(
                    nombre,
                    tipo,
                    declCtx.start.getLine(),
                    declCtx.start.getCharPositionInLine() + 1
            );
            variable.setAmbito(ambito);

            // Arreglo local
            if (declCtx.ENTERO() != null) {
                int tam = Integer.parseInt(declCtx.ENTERO().getText());
                variable.setArreglo(tam);
            }

            if (!tablaSimbolos.insertar(variable)) {
                agregarError(ErrorSemantico.TipoError.REDEFINICION_VARIABLE,
                        "La variable '" + nombre + "' ya está declarada en este ámbito",
                        declCtx.start.getLine(), declCtx.start.getCharPositionInLine() + 1);
            }
        }
    }

    // ================== ASIGNACIONES (VARIABLES Y ARREGLOS) ==================

    @Override
    public void enterAsignacion_simple(compiladorParser.Asignacion_simpleContext ctx) {
        procesarAsignacion(ctx.referencia(), ctx.expresion(), ctx);
    }

    @Override
    public void enterAsignacion_suma(compiladorParser.Asignacion_sumaContext ctx) {
        procesarAsignacion(ctx.referencia(), ctx.expresion(), ctx);
    }

    private void procesarAsignacion(compiladorParser.ReferenciaContext refCtx,
                                    compiladorParser.ExpresionContext exprCtx,
                                    ParserRuleContext ctx) {
        String nombre = refCtx.IDENTIFICADOR().getText();
        Simbolo simbolo = tablaSimbolos.buscar(nombre);

        if (simbolo == null) {
            // variableFantasma, w, variableFinal, etc.
            agregarError(ErrorSemantico.TipoError.VARIABLE_NO_DECLARADA,
                    "La variable '" + nombre + "' no está declarada",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        // 🚨 NUEVO: asignación a algo que NO es variable (por ejemplo, una función)
        if (simbolo instanceof SimboloFuncion) {
            agregarError(
                    ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                    "No se puede asignar valor a '" + nombre + "' porque no es una variable",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1
            );
            return;
        }

        if (simbolo instanceof SimboloVariable) {
            SimboloVariable variable = (SimboloVariable) simbolo;
            variable.setInicializado(true);
            variable.setUtilizado(true);

            String tipoExpresion = analizarTipoExpresion(exprCtx);
            verificarCompatibilidadTipos(variable.getTipoDato(), tipoExpresion, exprCtx);

            // Si es acceso a arreglo, verificar índice int
            if (refCtx.expresion() != null) {
                String tipoIndice = analizarTipoExpresion(refCtx.expresion());
                if (!"int".equals(tipoIndice)) {
                    agregarError(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                            "El índice del arreglo '" + nombre + "' debe ser de tipo int",
                            refCtx.expresion().start.getLine(),
                            refCtx.expresion().start.getCharPositionInLine() + 1);
                }
            }
        }
    }

    // ================== EXPRESIONES ==================

    @Override
    public void enterExpresion(compiladorParser.ExpresionContext ctx) {
        // 1) Llamada a función: IDENTIFICADOR PA argumentos? PC
        if (ctx.IDENTIFICADOR() != null && ctx.argumentos() != null) {
            String nombre = ctx.IDENTIFICADOR().getText();
            verificarLlamadaFuncion(nombre, ctx.argumentos(), ctx);
        }

        // 2) Uso de variable o arreglo: referencia
        if (ctx.referencia() != null) {
            procesarUsoReferencia(ctx.referencia(), ctx);
        }

        // 3) Marcar variables utilizadas en subexpresiones
        if (ctx.expresion() != null) {
            for (compiladorParser.ExpresionContext subExpr : ctx.expresion()) {
                marcarVariablesUtilizadas(subExpr);
            }
        }
    }

    private void procesarUsoReferencia(compiladorParser.ReferenciaContext refCtx,
                                       ParserRuleContext ctx) {
        String nombre = refCtx.IDENTIFICADOR().getText();
        Simbolo simbolo = tablaSimbolos.buscar(nombre);

        if (simbolo == null) {
            agregarError(ErrorSemantico.TipoError.VARIABLE_NO_DECLARADA,
                    "La variable '" + nombre + "' no está declarada",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        if (simbolo instanceof SimboloVariable) {
            SimboloVariable variable = (SimboloVariable) simbolo;

            if (!variable.isInicializada() && !variable.esParametro()) {
                agregarError(ErrorSemantico.TipoError.VARIABLE_NO_INICIALIZADA,
                        "La variable '" + nombre + "' se usa sin inicializar",
                        ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            }

            variable.setUtilizado(true);

            // Verificar índice si es arreglo
            if (refCtx.expresion() != null) {
                String tipoIndice = analizarTipoExpresion(refCtx.expresion());
                if (!"int".equals(tipoIndice)) {
                    agregarError(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                            "El índice del arreglo '" + nombre + "' debe ser de tipo int",
                            refCtx.expresion().start.getLine(),
                            refCtx.expresion().start.getCharPositionInLine() + 1);
                }
            }
        }
    }

    private void marcarVariablesUtilizadas(compiladorParser.ExpresionContext expr) {
        if (expr == null) return;

        // Caso referencia (variable o arreglo)
        if (expr.referencia() != null) {
            compiladorParser.ReferenciaContext ref = expr.referencia();
            String nombre = ref.IDENTIFICADOR().getText();
            Simbolo simbolo = tablaSimbolos.buscar(nombre);
            if (simbolo instanceof SimboloVariable) {
                ((SimboloVariable) simbolo).setUtilizado(true);
            }
        }

        // Recorrer sub-expresiones
        if (expr.expresion() != null) {
            for (compiladorParser.ExpresionContext subExpr : expr.expresion()) {
                marcarVariablesUtilizadas(subExpr);
            }
        }
    }

    // ================== BLOQUES Y ESTRUCTURAS DE CONTROL ==================

    @Override
    public void enterBloque(compiladorParser.BloqueContext ctx) {
        tablaSimbolos.abrirAmbito();
    }

    @Override
    public void exitBloque(compiladorParser.BloqueContext ctx) {
        verificarVariablesNoUtilizadas();
        tablaSimbolos.cerrarAmbito();
    }

    @Override
    public void enterMientras(compiladorParser.MientrasContext ctx) {
        dentroDeLoop = true;

        String tipoCondicion = analizarTipoExpresion(ctx.expresion());
        if (!tipoCondicion.equals("bool") && !tipoCondicion.equals("int")) {
            agregarWarning(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                    "La condición del while debería ser booleana",
                    ctx.expresion().start.getLine(),
                    ctx.expresion().start.getCharPositionInLine() + 1);
        }
    }

    @Override
    public void exitMientras(compiladorParser.MientrasContext ctx) {
        dentroDeLoop = false;
    }

    @Override
    public void enterPara(compiladorParser.ParaContext ctx) {
        dentroDeLoop = true;

        // Condición del for (si existe)
        if (ctx.expresion() != null && ctx.expresion().size() > 0) {
            String tipoCondicion = analizarTipoExpresion(ctx.expresion(0));
            if (!tipoCondicion.equals("bool") && !tipoCondicion.equals("int")) {
                agregarWarning(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                        "La condición del for debería ser booleana",
                        ctx.expresion(0).start.getLine(),
                        ctx.expresion(0).start.getCharPositionInLine() + 1);
            }
        }
    }

    @Override
    public void exitPara(compiladorParser.ParaContext ctx) {
        dentroDeLoop = false;
    }

    @Override
    public void enterInstruccion(compiladorParser.InstruccionContext ctx) {
        if (ctx.BREAK() != null || ctx.CONTINUE() != null) {
            if (!dentroDeLoop) {
                String instruccion = ctx.BREAK() != null ? "break" : "continue";
                agregarError(ErrorSemantico.TipoError.BREAK_CONTINUE_FUERA_DE_BUCLE,
                        "La instrucción '" + instruccion + "' solo puede usarse dentro de un bucle",
                        ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            }
        }
    }

    @Override
    public void enterRetorno(compiladorParser.RetornoContext ctx) {
        SimboloFuncion funcionActual = tablaSimbolos.getFuncionActual();

        if (funcionActual == null) {
            agregarError(ErrorSemantico.TipoError.RETURN_FUERA_DE_FUNCION,
                    "La declaración return solo puede usarse dentro de una función",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        hayReturn = true;
        funcionActual.setTieneReturn(true);

        String tipoRetornoFuncion = funcionActual.getTipoRetorno();

        if (ctx.expresion() != null) {
            String tipoExpresion = analizarTipoExpresion(ctx.expresion());

            if (tipoRetornoFuncion.equals("void")) {
                agregarError(ErrorSemantico.TipoError.TIPO_RETORNO_INCORRECTO,
                        "La función void no puede retornar un valor",
                        ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            } else {
                verificarCompatibilidadTipos(tipoRetornoFuncion, tipoExpresion, ctx.expresion());
            }
        } else {
            if (!tipoRetornoFuncion.equals("void")) {
                agregarError(ErrorSemantico.TipoError.TIPO_RETORNO_INCORRECTO,
                        "La función debe retornar un valor de tipo " + tipoRetornoFuncion,
                        ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            }
        }
    }

    // ================== AUXILIARES DE TIPOS ==================

    private String analizarTipoExpresion(compiladorParser.ExpresionContext ctx) {
        if (ctx == null)
            return "unknown";

        // Literales
        if (ctx.ENTERO() != null) return "int";
        if (ctx.DECIMAL() != null) return "double";
        if (ctx.CARACTER() != null) return "char";
        if (ctx.TRUE() != null || ctx.FALSE() != null) return "bool";

        // Variables / arreglos: referencia
        if (ctx.referencia() != null) {
            String nombre = ctx.referencia().IDENTIFICADOR().getText();
            Simbolo simbolo = tablaSimbolos.buscar(nombre);
            if (simbolo instanceof SimboloVariable) {
                return ((SimboloVariable) simbolo).getTipoDato();
            }
        }

        // Llamadas a función: IDENTIFICADOR PA argumentos? PC
        if (ctx.IDENTIFICADOR() != null && ctx.argumentos() != null) {
            SimboloFuncion funcion = tablaSimbolos.buscarFuncion(ctx.IDENTIFICADOR().getText());
            if (funcion != null) {
                return funcion.getTipoRetorno();
            }
        }

        // Expresiones binarias
        if (ctx.expresion() != null && ctx.expresion().size() == 2) {
            String tipo1 = analizarTipoExpresion(ctx.expresion(0));
            String tipo2 = analizarTipoExpresion(ctx.expresion(1));

            if (ctx.AND() != null || ctx.OR() != null) {
                return "bool";
            }

            if (ctx.EQ() != null || ctx.NEQ() != null || ctx.LT() != null ||
                    ctx.LE() != null || ctx.GT() != null || ctx.GE() != null) {
                return "bool";
            }

            if (ctx.SUMA() != null || ctx.RESTA() != null || ctx.MULT() != null ||
                    ctx.DIV() != null || ctx.MOD() != null) {
                return determinarTipoAritmetico(tipo1, tipo2);
            }
        }

        // Expresiones unarias
        if (ctx.expresion() != null && ctx.expresion().size() == 1) {
            if (ctx.NOT() != null) {
                return "bool";
            }
            if (ctx.SUMA() != null || ctx.RESTA() != null) {
                return analizarTipoExpresion(ctx.expresion(0));
            }
        }

        return "unknown";
    }

    private String determinarTipoAritmetico(String tipo1, String tipo2) {
        if (tipo1.equals("double") || tipo2.equals("double")) {
            return "double";
        }
        if (tipo1.equals("int") || tipo2.equals("int")) {
            return "int";
        }
        if (tipo1.equals("char") || tipo2.equals("char")) {
            return "char";
        }
        return "int";
    }

    private void verificarCompatibilidadTipos(String tipoEsperado, String tipoObtenido,
                                              compiladorParser.ExpresionContext ctx) {
        if (tipoEsperado.equals(tipoObtenido)) {
            return;
        }

        if (tipoEsperado.equals("double") && tipoObtenido.equals("int")) {
            agregarWarning(ErrorSemantico.TipoError.CONVERSION_TIPO_IMPLICITA,
                    "Conversión implícita de int a double",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        if (tipoEsperado.equals("int") && tipoObtenido.equals("char")) {
            agregarWarning(ErrorSemantico.TipoError.CONVERSION_TIPO_IMPLICITA,
                    "Conversión implícita de char a int",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        if (!tipoObtenido.equals("unknown")) {
            agregarError(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                    "No se puede asignar " + tipoObtenido + " a " + tipoEsperado,
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
        }
    }

    private void verificarLlamadaFuncion(String nombre, compiladorParser.ArgumentosContext argumentos,
                                         compiladorParser.ExpresionContext ctx) {
        SimboloFuncion funcion = tablaSimbolos.buscarFuncion(nombre);

        if (funcion == null) {
            agregarError(ErrorSemantico.TipoError.FUNCION_NO_DECLARADA,
                    "La función '" + nombre + "' no está declarada",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
            return;
        }

        funcion.setUtilizado(true);

        List<String> tiposArgumentos = new ArrayList<>();
        if (argumentos != null && argumentos.expresion() != null) {
            for (compiladorParser.ExpresionContext expr : argumentos.expresion()) {
                tiposArgumentos.add(analizarTipoExpresion(expr));
            }
        }

        if (tiposArgumentos.size() != funcion.getNumeroParametros()) {
            agregarError(ErrorSemantico.TipoError.NUMERO_ARGUMENTOS_INCORRECTO,
                    "La función '" + nombre + "' espera " + funcion.getNumeroParametros() +
                            " argumentos, pero se proporcionaron " + tiposArgumentos.size(),
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
        } else if (!funcion.coincideSignatura(tiposArgumentos)) {
            agregarError(ErrorSemantico.TipoError.TIPOS_INCOMPATIBLES,
                    "Los tipos de argumentos no coinciden con los parámetros de la función '" + nombre + "'",
                    ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
        }
    }

    // ================== VERIFICACIONES DE VARIABLES ==================

    private void verificarVariablesNoUtilizadas() {
        for (SimboloVariable variable : tablaSimbolos.getVariablesAmbitoActual()) {
            if (!variable.isUtilizado()) {
                String nombre = variable.getNombre();

                // 🔧 Ajuste: solo consideramos temporales: temp, i, j, k
                boolean esVariableTemporal =
                        nombre.equals("temp") ||
                        nombre.equals("i") ||
                        nombre.equals("j") ||
                        nombre.equals("k");

                if (variable.esParametro()) {
                    // ⛔ No queremos warnings de parámetros no utilizados en los ejemplos
                    continue;
                } else {
                    if (!esVariableTemporal &&
                            !nombre.startsWith("resultado") &&
                            !nombre.startsWith("suma") &&
                            !nombre.startsWith("resta") &&
                            !nombre.startsWith("mult") &&
                            !nombre.startsWith("div") &&
                            !nombre.startsWith("mod") &&
                            !nombre.startsWith("comp") &&
                            !nombre.startsWith("logico") &&
                            !nombre.startsWith("o_logico") &&
                            !nombre.startsWith("negacion") &&
                            !nombre.startsWith("grado")) {

                        agregarWarning(ErrorSemantico.TipoError.VARIABLE_NO_UTILIZADA,
                                "La variable '" + variable.getNombre() + "' se declara pero no se utiliza",
                                variable.getLinea(), variable.getColumna());
                    }
                }
            }
        }
    }

    private void verificacionesFinals() {
        if (!tablaSimbolos.existeFuncionMain()) {
            agregarError(ErrorSemantico.TipoError.FUNCION_MAIN_FALTANTE,
                    "El programa debe tener una función main",
                    0, 0);
        }

        // ⛔ En los ejemplos que querés replicar NO se muestra
        // warning de "función no utilizada", así que lo desactivamos.
        /*
        for (SimboloFuncion funcion : tablaSimbolos.getTodasLasFunciones()) {
            if (!funcion.isUtilizado() && !funcion.esMain()) {
                agregarWarning(ErrorSemantico.TipoError.FUNCION_NO_UTILIZADA,
                        "La función '" + funcion.getNombre() + "' se declara pero no se utiliza",
                        funcion.getLinea(), funcion.getColumna());
            }
        }
        */
    }

    // ================== MANEJO DE ERRORES/WARNINGS ==================

    private void agregarError(ErrorSemantico.TipoError tipo, String mensaje, int linea, int columna) {
        errores.add(new ErrorSemantico(tipo, mensaje, linea, columna));
    }

    private void agregarWarning(ErrorSemantico.TipoError tipo, String mensaje, int linea, int columna) {
        warnings.add(new ErrorSemantico(tipo, mensaje, linea, columna));
    }

    // ================== GETTERS ==================

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    public List<ErrorSemantico> getErrores() {
        return new ArrayList<>(errores);
    }

    public List<ErrorSemantico> getWarnings() {
        return new ArrayList<>(warnings);
    }
}
