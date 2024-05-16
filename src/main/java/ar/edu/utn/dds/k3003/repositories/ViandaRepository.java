package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ViandaRepository {

    private static AtomicLong seqId = new AtomicLong();
    private Collection<Vianda> viandas;

    public ViandaRepository() {
        this.viandas = new ArrayList<>();
    }

    public Vianda save(Vianda vianda) {
        if (Objects.isNull(vianda.getId())) {
        	vianda.setId(seqId.getAndIncrement());
            this.viandas.add(vianda);
        }
		return vianda;
    }
    public Collection<Vianda> getFullCollection() {
    	return viandas;
    }
    public Vianda buscarPorId(Long id) {
    	Optional<Vianda> first = this.viandas.stream().filter(x -> x.getId().equals(id)).findFirst();
    	return first.orElseThrow(() -> new NoSuchElementException(
                String.format("No hay una vianda de id: %s", id)
        ));
    }
    public Vianda buscarPorQr(String qr) {
        Optional<Vianda> first = this.viandas.stream().filter(x -> x.getQr().equals(qr)).findFirst();
        return first.orElseThrow(() -> new NoSuchElementException(
                String.format("No hay una vianda de qr: %s", qr)
        ));
    }
}