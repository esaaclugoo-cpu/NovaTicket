package com.ilerna.novaticket.service;

import com.ilerna.novaticket.model.Compra;
import com.ilerna.novaticket.repository.CompraDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    private final CompraDAO compraDAO;

    public CompraService(@Qualifier("compraDAOJdbc") CompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }

    public void guardarCompra(Compra compra) {compraDAO.guardar(compra);}

    public void actualizarCompra(Compra compra) {
        compraDAO.actualizar(compra);
    }

    public void eliminarCompra(int id) {compraDAO.eliminar(id);}

    public Compra obtenerCompraPorId(int id) {
        return compraDAO.obtenerPorId(id);
    }

    public List<Compra> listarTodasLasCompras() {
        return compraDAO.listarTodos();
    }
}
