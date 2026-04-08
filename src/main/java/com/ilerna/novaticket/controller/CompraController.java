package com.ilerna.novaticket.controller;

import com.ilerna.novaticket.model.Compra;
import com.ilerna.novaticket.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping("/compras")
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.listarTodasLasCompras());
        return "crudCompra";
    }

    @GetMapping("/compras/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("compra", new Compra());
        return "formCompra";
    }

    @PostMapping("/compras/guardar")
    public String guardarCompra(@ModelAttribute Compra compra, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMensaje", "Revisa los datos del formulario. La fecha debe tener formato valido (yyyy-MM-dd).");
            return "formCompra";
        }

        if (compra.getId() > 0) {
            compraService.actualizarCompra(compra);
        } else {
            compraService.guardarCompra(compra);
        }
        return "redirect:/compras";
    }

    @GetMapping("/compras/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Compra compra = compraService.obtenerCompraPorId(id);
        if (compra == null) {
            return "redirect:/compras";
        }
        model.addAttribute("compra", compra);
        return "formCompra";
    }

    @PostMapping("/compras/actualizar")
    public String actualizarCompra(@ModelAttribute Compra compra, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || compra.getId() <= 0) {
            model.addAttribute("errorMensaje", "No se pudo actualizar. Verifica la fecha y los campos obligatorios.");
            return "formCompra";
        }
        compraService.actualizarCompra(compra);
        return "redirect:/compras";
    }

    @GetMapping("/compras/eliminar/{id}")
    public String eliminarCompra(@PathVariable int id) {
        compraService.eliminarCompra(id);
        return "redirect:/compras";
    }
}
