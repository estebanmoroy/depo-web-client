package com.bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.entities.Familia;
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
	
	private List<Lote> listaLotes;
	
	public Lote getLote() {
		if(lote == null) {
			lote = new Lote();
		}
		return lote;
	}
	
	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	public List<Lote> getListaLotes() {
		if(listaLotes == null) {
			listaLotes = loteBean.obtenerTodos();
		}
		return listaLotes;
	}

	public void setListaLotes(List<Lote> listaLotes) {
		this.listaLotes = listaLotes;
	}
	
	public String obtenerTodosLosLotes(){
		setListaLotes(ordenarPorCodigo(loteBean.obtenerTodos()));
		return QUEDARSE;
	}
	
	public String obtenerListaPorCodigo(String filtro){
		setListaLotes(ordenarPorCodigo(loteBean.obtenerTodosPorCodigo(filtro)));
		return QUEDARSE;
	}
	
	private List<Lote> ordenarPorCodigo(List<Lote> lista){
		Collections.sort(lista, new Comparator<Lote>() {
			@Override
			public int compare(Lote l1, Lote l2) {
				return l1.getCodigo().compareTo(l2.getCodigo());
			}
		});
		return lista;
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
