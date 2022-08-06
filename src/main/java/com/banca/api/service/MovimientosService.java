package com.banca.api.service;

import com.banca.api.core.base.BaseService;
import com.banca.api.core.utils.Constants;
import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.Cuenta;
import com.banca.api.entity.Movimientos;
import com.banca.api.model.MovimientoMapper;
import com.banca.api.model.MovimientoModel;
import com.banca.api.repo.CuentaRepo;
import com.banca.api.repo.MovimientosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientosService extends BaseService<MovimientosService, MovimientoModel> {

    @Autowired
    private MovimientosRepo movimientosRepo;
    @Autowired
    private CuentaRepo cuentaRepo;

    public MovimientosService() {
        super(MovimientosService.class);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<MovimientoModel>>> getAll() {
        List<Movimientos> movimientos = movimientosRepo.findAll();
        if (!movimientos.isEmpty()) {
            List<MovimientoModel> movimientosModel = MovimientoMapper.movimientosToModels(movimientos);
            return (ResponseEntity<ResponseWrapper<List<MovimientoModel>>>) super.buildResponse(movimientosModel);
        }
        return (ResponseEntity<ResponseWrapper<List<MovimientoModel>>>) super.buildResponse();
    }

    public ResponseEntity<ResponseWrapper<MovimientoModel>> create(MovimientoModel movimientoModel) {
        boolean existeCuenta = (movimientoModel.getNumeroCuenta() != null && !movimientoModel.getNumeroCuenta().trim().isEmpty()) ? true : false;
        boolean existeTipo = (movimientoModel.getTipoMovimiento() != null && !movimientoModel.getTipoMovimiento().trim().isEmpty()) ? true : false;
        boolean existeValor = (movimientoModel.getValor() != null && !movimientoModel.getValor().isNaN()) ? true : false;
        if (!existeCuenta || !existeTipo || !existeValor) {
            return (ResponseEntity<ResponseWrapper<MovimientoModel>>) super.buildBadResponse(HttpStatus.BAD_REQUEST, "Ingrese los valores necesarios para crear registrar un movimiento");
        }
        Cuenta cuenta = new Cuenta();
        Movimientos movimiento = MovimientoMapper.modelToMovimiento(movimientoModel);
        var cuentaDb = cuentaRepo.findOne((root, query, cb) -> cb.equal(root.get("numeroCuenta"), movimientoModel.getNumeroCuenta()));
        if (cuentaDb.isPresent()) {
            Double Saldo = this.getSaldoActual(cuentaDb.get().getId());
            if (movimiento.getTipoMovimiento().equals(Constants.TIPO_MOVIMIENTO_RETIRO)) {
                if (Saldo < movimiento.getValor()) {
                    return (ResponseEntity<ResponseWrapper<MovimientoModel>>) super.buildBadResponse(HttpStatus.BAD_REQUEST, "Su saldo actual no es suficiente para cubrir el retiro");
                }
            }
            movimiento.setSaldoInicial(Saldo);
            movimiento.setSaldo(Saldo + movimiento.getValor());
            movimiento.setCuenta(cuentaDb.get());
            movimientosRepo.save(movimiento);
            return (ResponseEntity<ResponseWrapper<MovimientoModel>>) super.buildResponse(MovimientoMapper.movimientoToModel(movimiento));
        } else {
            return (ResponseEntity<ResponseWrapper<MovimientoModel>>) super.buildBadResponse(HttpStatus.NO_CONTENT, "El número de cuenta no existe");
        }
    }

    private double getSaldoActual(Long idCuenta) {
        var movimientosDb = movimientosRepo.findAll((root, query, cb) -> cb.equal(root.get("cuenta"), idCuenta), Sort.by(Sort.Direction.DESC, "id"));
        if (movimientosDb.isEmpty()) {
            var cuentaDb = cuentaRepo.findAll((root, query, cb) -> cb.equal(root.get("id"), idCuenta), Sort.by(Sort.Direction.ASC, "id")).get(0);
            return cuentaDb.getSaldoInicial();
        } else {
            return movimientosDb.get(0).getSaldo();
        }
    }
}
