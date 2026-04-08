package com.ilerna.novaticket.repository;

import com.ilerna.novaticket.model.Asiento;
import com.ilerna.novaticket.model.Compra;

import java.util.List;

public interface CompraDAO {

    void guardar(Compra compra);
    void actualizar(Compra compra);
    void eliminar(int id);
    Compra obtenerPorId(int id);
    List<Compra> listarTodos();

}
