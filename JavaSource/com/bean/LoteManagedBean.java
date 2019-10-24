package com.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.entities.Lote;
import com.services.LoteBeanRemote;
import com.exception.ServicesException;

@ManagedBean
@RequestScoped
public class LoteManagedBean {
	
	@EJB
	private LoteBeanRemote loteBean;
	
	private static final String MOSTRAR_TODOS = "mostrarTodosLosLotes";
	private static final String DETALLES = "verDetallesLote";
	private static final String CREAR = "crearLote";
	private static final String ACTUALIZAR = "actualizarLote";
	private static final String	ELIMINAR = "eliminarLote";
	private static final String QUEDARSE = null;
	
	private Lote lote;
	
	public Lote getLote() {
		if(lote == null) {
			lote = new Lote();
		}
		return lote;
	}
	
	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	public List<Lote> obtenerTodosLosLotes(){
		return loteBean.obtenerTodos();
	}
	
	public List<Lote> obtenerListaPorCodigo(String filtro){
		//TODO
		return null;
	}
	
	public List<Lote> obtenerListaPorNombre(String filtro){
		//TODO
		return null;
	}
	
	public List<Lote> obtenerListaPorDescripcion(String filtro){
		//TODO
		return null;
	}
	
	public Lote obtenerLotePorCodigo() {
		//TODO
		return lote;
	}
	
	public String verDetallesLote() {
		return DETALLES;
	}
	
	public String crearLoteStart() {
		return CREAR;
	}
	
	public String crearLoteEnd() {
		try {
			loteBean.crear(lote);
		}catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Crear Exitosa");
		return MOSTRAR_TODOS;
	}

	public String actualizarLoteStart() {
		return ACTUALIZAR;
	}
	
	public String actualizarLoteEnd() {
		try {
			loteBean.actualizar(lote);
		} catch(ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Actualizar Exitosa");
		return MOSTRAR_TODOS;
	}
	
	public String eliminarLoteStart() {
		return ELIMINAR;
	}
	
	public String eliminarLoteEnd() {
		try {
			loteBean.eliminar(lote);
		}catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Eliminar Exitosa");
		return MOSTRAR_TODOS;
	}
	
	private void enviarMensajeAlUsuario(String mensaje) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
	}
	
	private void enviarMensajeDeErrorAlUsuario(String mensaje) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
	}
	
	private FacesContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}

}
