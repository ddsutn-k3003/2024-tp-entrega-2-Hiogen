package ar.edu.utn.dds.k3003.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import ar.edu.utn.dds.k3003.facades.FachadaColaboradores;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;
import ar.edu.utn.dds.k3003.repositories.ViandaMapper;
import ar.edu.utn.dds.k3003.repositories.ViandaRepository;

public class Fachada implements ar.edu.utn.dds.k3003.facades.FachadaViandas {

    private final ViandaRepository viandaRepository;
    private final ViandaMapper viandaMapper;
    
    private FachadaHeladeras fachadaHeladeras;
    //private FachadaColaboradores fachadaColaborador;
   
    public Fachada() {
    	this.viandaMapper = new ViandaMapper();
    	this.viandaRepository = new ViandaRepository();
    }
    
    @Override
    public ViandaDTO agregar(ViandaDTO viandaDTO) {
    	Vianda vianda = new Vianda(viandaDTO.getCodigoQR(), viandaDTO.getFechaElaboracion(), EstadoViandaEnum.PREPARADA, viandaDTO.getColaboradorId(), viandaDTO.getHeladeraId());
    	vianda = this.viandaRepository.save(vianda);
    	return viandaMapper.map(vianda);
    }
    
    @Override
    public ViandaDTO modificarEstado(String qr, EstadoViandaEnum estado) {
    	Vianda temp = viandaRepository.buscarPorQr(qr);
    	Vianda nuevaVianda = new Vianda(temp.getQr(), temp.getFechaElaboracion(), estado, temp.getColaboradorId(), temp.getHeladeraId());
    	long tempId = temp.getId() - 1;
    	viandaRepository.getFullCollection().remove(temp);
    	nuevaVianda.setId(tempId);
    	viandaRepository.getFullCollection().add(nuevaVianda);
    	return viandaMapper.map(nuevaVianda);
    }
    
    @SuppressWarnings("null")
	@Override
    public List<ViandaDTO> viandasDeColaborador(Long colaboradorId, Integer mes, Integer anio) {
    	List<ViandaDTO> viandasColaborador = new ArrayList<>();

        for (Vianda vianda : this.viandaRepository.getFullCollection()) {
            LocalDateTime fechaVianda = vianda.getFechaElaboracion();
            if (vianda.getColaboradorId().equals(colaboradorId) &&
                    fechaVianda.getMonthValue() == mes &&
                    fechaVianda.getYear() == anio) {
            	viandasColaborador.add(viandaMapper.map(vianda));
            }
        }

        return viandasColaborador;
    }
    
    @Override
    public ViandaDTO buscarXQR(String qr) {
    	Vianda vianda = viandaRepository.buscarPorQr(qr);
    	return viandaMapper.map(vianda);
    }
    
    @Override
    public boolean evaluarVencimiento(String qr) {
    	ViandaDTO vianda = this.buscarXQR(qr);
    	Optional<TemperaturaDTO> temperaturasDTO = fachadaHeladeras.obtenerTemperaturas(vianda.getHeladeraId()).stream().filter(x -> x.getTemperatura() > 5).findAny();	
    	if(temperaturasDTO.isEmpty()) {
    		return true;
    	} else {
    		return false;
    	}
    }

	@Override
	public void setHeladerasProxy(FachadaHeladeras fachadaHeladerasInstancia) {
		this.fachadaHeladeras = fachadaHeladerasInstancia;	
	}

	@Override
	public ViandaDTO modificarHeladera(String qrVianda, int heladeraDestino) {
		Vianda vianda = viandaRepository.buscarPorQr(qrVianda);
        vianda.setHeladeraId(heladeraDestino);
        return viandaMapper.map(vianda);
	}
}