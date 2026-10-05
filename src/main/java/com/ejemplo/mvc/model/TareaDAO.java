package com.ejemplo.mvc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TareaDAO {
    private static final List<Tarea> datos = new ArrayList<>();
    private static int contador = 3;

    static {
        Date hoy = new Date();
        datos.add(new Tarea(1, "Dise\u00f1ar el diagrama de clases MVC",
            "Dise\u00f1o", "Alta", sumarDias(hoy, 2)));
        datos.add(new Tarea(2, "Implementar el Front Controller",
            "Desarrollo", "Alta", sumarDias(hoy, 4)));
        datos.add(new Tarea(3, "Redactar el README con decisiones de dise\u00f1o",
            "Documentaci\u00f3n", "Media", sumarDias(hoy, 7)));
    }

    private static Date sumarDias(Date base, int dias) {
        long unDiaMs = 24L * 60 * 60 * 1000;
        return new Date(base.getTime() + dias * unDiaMs);
    }

    public List<Tarea> findAll() {
        return Collections.unmodifiableList(datos);
    }

    public Tarea findById(int id) {
        return datos.stream()
                    .filter(t -> t.getId() == id)
                    .findFirst().orElse(null);
    }

    public void save(Tarea t) {
        t.setId(++contador);
        datos.add(t);
    }

    public void delete(int id) {
        datos.removeIf(t -> t.getId() == id);
    }
}
