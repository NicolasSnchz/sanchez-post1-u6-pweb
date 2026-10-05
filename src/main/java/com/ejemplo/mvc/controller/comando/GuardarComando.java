package com.ejemplo.mvc.controller.comando;

import com.ejemplo.mvc.model.Tarea;
import com.ejemplo.mvc.service.TareaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class GuardarComando implements Comando {
    private final TareaService service;

    public GuardarComando(TareaService service) { this.service = service; }

    @Override
    public String ejecutar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String titulo    = req.getParameter("titulo");
        String categoria = req.getParameter("categoria");
        String prioridad = req.getParameter("prioridad");
        String fechaStr  = req.getParameter("fechaLimite");

        int maxLongitud = (Integer)
            req.getServletContext().getAttribute("maxLongitudTitulo");
        Map<String, String> errores = new LinkedHashMap<>();

        if (titulo == null || titulo.trim().isEmpty()) {
            errores.put("titulo", "El t\u00edtulo es obligatorio.");
        } else if (titulo.trim().length() > maxLongitud) {
            errores.put("titulo",
                "El t\u00edtulo no debe superar " + maxLongitud + " caracteres.");
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            errores.put("categoria", "La categor\u00eda es obligatoria.");
        }

        if (!"Alta".equals(prioridad) && !"Media".equals(prioridad)
                && !"Baja".equals(prioridad)) {
            errores.put("prioridad", "Seleccione una prioridad v\u00e1lida.");
        }

        Date fechaLimite = null;
        try {
            // ISO_LOCAL_DATE es estricto: rechaza 2026-02-30 o 20/08/2026
            LocalDate fecha = LocalDate.parse(fechaStr == null ? "" : fechaStr.trim());
            if (fecha.isBefore(LocalDate.now())) {
                errores.put("fechaLimite", "La fecha l\u00edmite no puede estar en el pasado.");
            } else {
                fechaLimite = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (DateTimeParseException e) {
            errores.put("fechaLimite", "Use el formato yyyy-MM-dd (ej: 2026-08-20).");
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores",     errores);
            req.setAttribute("titulo",      titulo);
            req.setAttribute("categoria",   categoria);
            req.setAttribute("prioridad",   prioridad);
            req.setAttribute("fechaLimite", fechaStr);
            return "/WEB-INF/views/formulario.jsp";
        }

        service.guardar(new Tarea(0, titulo.trim(), categoria.trim(),
            prioridad, fechaLimite));
        resp.sendRedirect(req.getContextPath() + "/app?comando=listar");
        return null;
    }
}
