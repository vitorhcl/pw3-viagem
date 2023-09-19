package br.com.etechoracio.viagem.controller;

import br.com.etechoracio.viagem.entity.Viagem;
import br.com.etechoracio.viagem.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/viagens")
public class ViagemController {
    @Autowired
    private ViagemRepository repository;

    @GetMapping
    public List<Viagem> buscarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Viagem> viagem = repository.findById(id);
        if(viagem.isPresent())
            return ResponseEntity.ok(viagem.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Viagem inserir(@RequestBody Viagem body) {
        Viagem inserida = repository.save(body);
        return inserida;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id)
    {
        Optional<Viagem> existe = repository.findById(id);
        if(existe.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody Viagem obj, @PathVariable Long id) {
        ResponseEntity<Object> responseEntity = buscarPorId(id);
        if(responseEntity.hasBody()) {
            Viagem viagem = (Viagem)responseEntity.getBody();
            repository.save(viagem);
            return obj;
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
