package com.ilerna.novaticket.controller;

import com.ilerna.novaticket.model.*;
import com.ilerna.novaticket.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        return "index";
    }

    // Listar todos los eventos para "/" y "/eventos"
    @GetMapping({"/", "/eventos"})
    public String listarEvento(Model model) {
        model.addAttribute("eventos", eventoService.listarTodosLosEventos());
        return "crudtest";
    }




    // Mostrar formulario para agregar un nuevo evento
    @GetMapping("/eventos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("evento", new EventoForm());
        return "formEvento";
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute EventoForm eventoForm) {
        Evento evento = null;
        switch (eventoForm.getTipo_evento()) {
            case concierto:
                Concierto c = new Concierto();
                c.setArtista_principal(eventoForm.getArtista_principal());
                c.setGenero_musical(eventoForm.getGenero_musical());
                c.setDuracion_minutos(eventoForm.getDuracion_minutos());
                evento = c;
                break;
            case teatro:
                Teatro t = new Teatro();
                t.setObra(eventoForm.getObra());
                t.setDirector(eventoForm.getDirector());
                evento = t;
                break;
            case museo:
                Museo m = new Museo();
                m.setNombre_exposicion(eventoForm.getNombre_exposicion());
                m.setTipo_exposicion(eventoForm.getTipo_exposicion());
                m.setFecha_fin(eventoForm.getFecha_fin());
                evento = m;
                break;
        }
        if (evento != null) {
            evento.setTipo_evento(eventoForm.getTipo_evento());
            evento.setNombre(eventoForm.getNombre());
            evento.setDescripcion(eventoForm.getDescripcion());
            evento.setFecha(eventoForm.getFecha());
            evento.setAforo_maximo(eventoForm.getAforo_maximo());
            evento.setId_lugar(eventoForm.getId_lugar());
            evento.setNombre_lugar(eventoForm.getNombre_lugar());
            evento.setDireccion(eventoForm.getDireccion());
            evento.setCiudad(eventoForm.getCiudad());
            evento.setRuta_imagen(eventoForm.getRuta_imagen());
            eventoService.guardarEvento(evento);
        }
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Evento evento = eventoService.obtenerEventoPorId(id);
        if (evento == null) {
            return "redirect:/eventos";
        }

        EventoForm form = new EventoForm();
        form.setId(evento.getId());
        form.setNombre(evento.getNombre());
        form.setDescripcion(evento.getDescripcion());
        form.setFecha(evento.getFecha());
        form.setAforo_maximo(evento.getAforo_maximo());
        form.setTipo_evento(evento.getTipo_evento());
        form.setId_lugar(evento.getId_lugar());
        form.setNombre_lugar(evento.getNombre_lugar());
        form.setDireccion(evento.getDireccion());
        form.setCiudad(evento.getCiudad());
        form.setRuta_imagen(evento.getRuta_imagen());

        if (evento instanceof Concierto) {
            Concierto c = (Concierto) evento;
            form.setArtista_principal(c.getArtista_principal());
            form.setGenero_musical(c.getGenero_musical());
            form.setDuracion_minutos(c.getDuracion_minutos());
        } else if (evento instanceof Teatro) {
            Teatro t = (Teatro) evento;
            form.setObra(t.getObra());
            form.setDirector(t.getDirector());
        } else if (evento instanceof Museo) {
            Museo m = (Museo) evento;
            form.setNombre_exposicion(m.getNombre_exposicion());
            form.setTipo_exposicion(m.getTipo_exposicion());
            form.setFecha_fin(m.getFecha_fin());
        }

        model.addAttribute("evento", form);
        return "formEvento";
    }

    @PostMapping("/eventos/actualizar")
    public String actualizarEvento(@ModelAttribute EventoForm eventoForm) {
        Evento evento = null;
        switch (eventoForm.getTipo_evento()) {
            case concierto:
                Concierto c = new Concierto();
                c.setArtista_principal(eventoForm.getArtista_principal());
                c.setGenero_musical(eventoForm.getGenero_musical());
                c.setDuracion_minutos(eventoForm.getDuracion_minutos());
                evento = c;
                break;
            case teatro:
                Teatro t = new Teatro();
                t.setObra(eventoForm.getObra());
                t.setDirector(eventoForm.getDirector());
                evento = t;
                break;
            case museo:
                Museo m = new Museo();
                m.setNombre_exposicion(eventoForm.getNombre_exposicion());
                m.setTipo_exposicion(eventoForm.getTipo_exposicion());
                m.setFecha_fin(eventoForm.getFecha_fin());
                evento = m;
                break;
        }
        if (evento != null) {
            evento.setId(eventoForm.getId());
            evento.setTipo_evento(eventoForm.getTipo_evento());
            evento.setNombre(eventoForm.getNombre());
            evento.setDescripcion(eventoForm.getDescripcion());
            evento.setFecha(eventoForm.getFecha());
            evento.setAforo_maximo(eventoForm.getAforo_maximo());
            evento.setId_lugar(eventoForm.getId_lugar());
            evento.setNombre_lugar(eventoForm.getNombre_lugar());
            evento.setDireccion(eventoForm.getDireccion());
            evento.setCiudad(eventoForm.getCiudad());
            evento.setRuta_imagen(eventoForm.getRuta_imagen());
            eventoService.actualizarEvento(evento);
        }
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable int id) {
        eventoService.eliminarEvento(id);
        return "redirect:/eventos";
    }
}
