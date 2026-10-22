package com.cibertec.puestosservice.services;

import com.cibertec.puestosservice.clients.*;
import com.cibertec.puestosservice.entities.Puesto;
import com.cibertec.puestosservice.models.*;
import com.cibertec.puestosservice.repositories.*;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import feign.FeignException;
import java.util.List;

@Service
public class PuestoService {
    private final PuestoRepository repo;
    private final GiroService giros;
    private final SociosClient socios;
    private final CobranzasClient cobranzas;
    private final PagosClient pagos;
    private final CircuitBreakerFactory<?,?> breakers;

    public PuestoService(PuestoRepository repo,GiroService giros,SociosClient socios,
                         CobranzasClient cobranzas,PagosClient pagos,CircuitBreakerFactory<?,?> breakers) {
        this.repo=repo; this.giros=giros; this.socios=socios; this.cobranzas=cobranzas; this.pagos=pagos; this.breakers=breakers;
    }

    @Transactional(readOnly=true)
    public List<PuestoDTO> listar() { return repo.findAll().stream().map(this::dto).toList(); }
    @Transactional(readOnly=true)
    public List<PuestoDTO> listarPorSocio(Long socioId) { return repo.findBySocioId(socioId).stream().map(this::dto).toList(); }
    @Transactional(readOnly=true)
    public PuestoDTO buscar(Long id) { return dto(obtener(id)); }

    @Transactional
    public PuestoDTO crear(PuestoDTO d) {
        if(repo.existsByNumero(d.numero())) throw new ResponseStatusException(HttpStatus.CONFLICT,"El numero de puesto ya existe");
        verificarSocio(d.socioId());
        Puesto p=new Puesto();
        asignar(p,d);
        return dto(repo.save(p));
    }
    @Transactional
    public PuestoDTO editar(Long id,PuestoDTO d) {
        Puesto p=obtener(id);
        if(repo.existsByNumeroAndIdNot(d.numero(),id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"El numero de puesto ya existe");
        verificarSocio(d.socioId());
        asignar(p,d);
        return dto(repo.save(p));
    }
    @Transactional
    public void eliminar(Long id) { repo.delete(obtener(id)); }

    public ResumenCuentasDTO resumen(Long id) {
        obtener(id);
        return breakers.create("resumenPuesto").run(
            () -> new ResumenCuentasDTO(cobranzas.listarPorPuesto(id),pagos.listarPorPuesto(id)),
            error -> resumenFallback(id,error)
        );
    }
    public ResumenCuentasDTO resumenFallback(Long id,Throwable error) {
        if(error instanceof ResponseStatusException ex && ex.getStatusCode().value()==404) throw ex;
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"No se pudo consultar el resumen financiero",error);
    }

    private void verificarSocio(Long socioId) {
        if(socioId==null) return;
        try { socios.buscarPorId(socioId); }
        catch(FeignException.NotFound ex) { throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Socio no encontrado: "+socioId,ex); }
        catch(FeignException ex) { throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"No disponible socios-service",ex); }
    }
    private Puesto obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Puesto no encontrado"));
    }
    private void asignar(Puesto p,PuestoDTO d) {
        p.setNumero(d.numero()); p.setSocioId(d.socioId()); p.setGiro(giros.obtener(d.giroId()));
        p.setInquilinoNombre(d.inquilinoNombre()); p.setInquilinoDocumento(d.inquilinoDocumento());
        p.setFechaInicioVigencia(d.fechaInicioVigencia()); p.setFechaFinVigencia(d.fechaFinVigencia());
    }
    private PuestoDTO dto(Puesto p) {
        return new PuestoDTO(p.getId(),p.getNumero(),p.getSocioId(),p.getGiro().getId(),
            p.getInquilinoNombre(),p.getInquilinoDocumento(),p.getFechaInicioVigencia(),p.getFechaFinVigencia());
    }
}
