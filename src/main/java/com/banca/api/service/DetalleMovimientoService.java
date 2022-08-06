package com.banca.api.service;

import com.banca.api.core.base.BaseService;
import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.entity.DetalleMovimientoView;
import com.banca.api.repo.DetalleMovimientoViewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class DetalleMovimientoService extends BaseService<DetalleMovimientoService, DetalleMovimientoView> {
    @Autowired
    private DetalleMovimientoViewRepo detalleRepo;

    public DetalleMovimientoService() {
        super(DetalleMovimientoService.class);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getAll() {
        List<DetalleMovimientoView> movimientos = detalleRepo.findAll();
        if (!movimientos.isEmpty()) {

            return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse(movimientos);
        }
        return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getByClient(Long cliente) {
        List<DetalleMovimientoView> movimientos = detalleRepo.findAll((root, query, cb) -> cb.equal(root.get("cliente"), cliente));
        if (!movimientos.isEmpty()) {

            return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse(movimientos);
        }
        return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>> getByClientAndFecha(int cliente, String fecha) throws ParseException {
        Calendar ini = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        ini.setTime(sdf.parse(fecha));
        ini.set(Calendar.MINUTE, 0);
        ini.set(Calendar.HOUR, 0);
        fin.setTime(sdf.parse(fecha));
        fin.set(Calendar.MINUTE, 59);
        fin.set(Calendar.HOUR, 23);

        super.LOG.info("---------------------" + fecha + "  " + ini.getTime());
        List<DetalleMovimientoView> movimientos = detalleRepo.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("cliente"), cliente),
                cb.between(root.get("fecha"), ini.getTime(), fin.getTime()))
        );
        if (!movimientos.isEmpty()) {

            return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse(movimientos);
        }
        return (ResponseEntity<ResponseWrapper<List<DetalleMovimientoView>>>) super.buildResponse();
    }
}
