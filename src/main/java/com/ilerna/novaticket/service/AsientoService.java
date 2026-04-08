package com.ilerna.novaticket.service;

import com.ilerna.novaticket.model.Asiento;
import com.ilerna.novaticket.repository.AsientoDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoService {

    private final AsientoDAO asientoDAO;

    public AsientoService(@Qualifier("asientoDAOJdbc") AsientoDAO asientoDAO) {
        this.asientoDAO = asientoDAO;
    }

    public void guardarAsiento(Asiento asiento) {
        asientoDAO.guardar(asiento);
    }

    public void actualizarAsiento(Asiento asiento) {
        asientoDAO.actualizar(asiento);
    }

    public void eliminarAsiento(int id) {
        asientoDAO.eliminar(id);
    }

    public Asiento obtenerAsientoPorId(int id) {
        return asientoDAO.obtenerPorId(id);
    }

    public List<Asiento> listarTodosLosAsientos() {
        return asientoDAO.listarTodos();
    }
}
