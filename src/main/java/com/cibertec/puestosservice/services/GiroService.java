package com.cibertec.puestosservice.services;

import com.cibertec.puestosservice.entities.Giro;
import com.cibertec.puestosservice.models.GiroDTO;
import com.cibertec.puestosservice.repositories.GiroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class GiroService {
    private final GiroRepository repo;
    public GiroService(GiroRepository repo) { this.repo=repo; }
    public List<GiroDTO> listar() { return repo.findAll().stream().map(this::dto).toList(); }
    public GiroDTO buscar(Long id) { return dto(obtener(id)); }
    public GiroDTO crear(GiroDTO d) {
        if(repo.existsByNombre(d.nombre())) throw new ResponseStatusException(HttpStatus.CONFLICT,"El giro ya existe");
        Giro g=new Giro(null,d.nombre(),d.descripcion());
        return dto(repo.save(g));
    }
    public GiroDTO editar(Long id,GiroDTO d) {
        Giro g=obtener(id);
        if(repo.existsByNombreAndIdNot(d.nombre(),id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"El giro ya existe");
        g.setNombre(d.nombre()); g.setDescripcion(d.descripcion());
        return dto(repo.save(g));
    }
    public void eliminar(Long id) {
        Giro g=obtener(id);
        repo.delete(g);
    }
    public Giro obtener(Long id) { return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Giro no encontrado")); }
    private GiroDTO dto(Giro g) { return new GiroDTO(g.getId(),g.getNombre(),g.getDescripcion()); }
}
