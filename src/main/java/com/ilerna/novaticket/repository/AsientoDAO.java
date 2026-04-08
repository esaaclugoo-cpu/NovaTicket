package com.ilerna.novaticket.repository;

import com.ilerna.novaticket.model.Asiento;
import com.ilerna.novaticket.model.Evento;

import java.util.List;

public interface AsientoDAO {

    void guardar(Asiento asiento);
    void actualizar(Asiento asiento);
    void eliminar(int id);
    Asiento obtenerPorId(int id);
    List<Asiento> listarTodos();

}
