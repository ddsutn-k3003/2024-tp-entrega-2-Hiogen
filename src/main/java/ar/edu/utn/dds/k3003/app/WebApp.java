package ar.edu.utn.dds.k3003.app;


import ar.edu.utn.dds.k3003.controller.ViandaController;
/*import ar.edu.utn.dds.k3003.clients.HeladerasProxy;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;*/
import io.javalin.Javalin;

public class WebApp {
	
	public static void main(String[] args) {
		
		var port = Integer.parseInt(System.getProperty("PORT", "8080"));
		var app = Javalin.create().start(port);
		//var objectMapper = createObjectMapper();
		
		var fachada = new Fachada();
		//fachada.setHeladerasProxy(new HeladerasProxy(objectMapper));
		
		var viandaController = new ViandaController(fachada);
		
		app.get("/", ctx -> ctx.result("Vianda"));
		app.post("/viandas", ctx -> viandaController.agregar(ctx));
		app.get("/viandas/search/findByColaboradorIdAndAnioAndMes", ctx -> viandaController.obtenerXColIDAndAnioAndMes(ctx));
		app.get("/viandas/{qr}", ctx -> viandaController.obtenerXQR(ctx));
		app.get("/viandas/{qr}/vencida", ctx -> viandaController.evaluarVencimiento(ctx));
		app.patch("/viandas/{qrVianda}", ctx -> viandaController.modificarHeladeraXQR(ctx));
	
	}

	/*private static ObjectMapper createObjectMapper() {
		var objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new JavaTimeModule());
	    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    objectMapper.setDateFormat(sdf);
	    return objectMapper;
	}*/
}