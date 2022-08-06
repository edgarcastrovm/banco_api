package com.banca.api.model;

import com.banca.api.entity.Movimientos;
import com.banca.api.entity.Persona;

import java.util.ArrayList;
import java.util.List;

public class MovimientoMapper {

    MovimientoMapper() {
    }

    public static Movimientos modelToMovimiento(MovimientoModel model) {
        Movimientos movimiento = new Movimientos();
        movimiento.setId(model.getId());
        movimiento.setFecha(model.getFecha());
        movimiento.setTipoMovimiento(model.getTipoMovimiento());
        movimiento.setValor(model.getValor());
        movimiento.setSaldo(model.getSaldo());
        //movimiento.setDireccion(model.getDireccion());
        movimiento.setEstado(movimiento.getEstado());
        return movimiento;
    }

    public static MovimientoModel movimientoToModel(Movimientos movimiento) {
        MovimientoModel model = new MovimientoModel();
        model.setId(movimiento.getId());
        model.setFecha(movimiento.getFecha());
        model.setTipoMovimiento(movimiento.getTipoMovimiento());
        model.setValor(movimiento.getValor());
        model.setSaldo(movimiento.getSaldo());
        model.setEstado(movimiento.getEstado());
        if (movimiento.getCuenta() != null) {
            model.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        }
        return model;
    }

    public static List<MovimientoModel> movimientosToModels(List<Movimientos> movimientos) {
        List<MovimientoModel> models = new ArrayList<>();
        for (Movimientos p : movimientos) {
            models.add(movimientoToModel(p));
        }
        return models;
    }
}
