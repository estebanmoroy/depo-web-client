package com.bean;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.entities.Familia;
import com.exception.ServicesException;
import com.services.FamiliaBeanRemote;

@ManagedBean
@RequestScoped
public class FamiliaManagedBean {

	@EJB
	private FamiliaBeanRemote familiaBean;
	
	private static final String MOSTRAR_TODOS = "mostrarTodasLasFamilias";
	private static final String DETALLES = "verDetallesFamilia";
	private static final String CREAR = "crearFamilia";
	private static final String ACTUALIZAR = "actualizarFamilia";
	private static final String	ELIMINAR = "eliminarFamilia";
	private static final String QUEDARSE = null;
	
	private Familia familia;
	
	private List<Familia> listaFamilias;
	
	public Familia getFamilia() {
		if (familia == null) {
			familia = new Familia();
		}
		return familia;
	}
	
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	public List<Familia> getListaFamilias() {
		if (listaFamilias == null) {
			listaFamilias = familiaBean.obtenerTodos();
		}
		return listaFamilias;
	}
	
	public void setListaFamilias(List<Familia> listaFamilias) {
		this.listaFamilias = listaFamilias;
	}
	
	public String obtenerTodasLasFamilias(){
		setListaFamilias(familiaBean.obtenerTodos());
		return QUEDARSE;
	}
	
	public String obtenerListaPorNombre(String filtro){
		setListaFamilias(ordenarPorNombre(familiaBean.obtenerTodosPorNombre(filtro)));
		return QUEDARSE;
	}
	
	private List<Familia> ordenarPorNombre(List<Familia> lista){
		Collections.sort(lista, new Comparator<Familia>() {
			@Override
			public int compare(Familia f1, Familia f2) {
				return f1.getNombre().compareTo(f2.getNombre());
			}
		});
		return lista;
	}
	
	public String mostrarTodasLasFamilias() {
		return MOSTRAR_TODOS;
	}
	public String verDetallesFamilia() {
		return DETALLES;
	}
	
	public String crearFamiliaStart() {
		return CREAR;
	}
	
	public String crearFamiliaEnd() {
		try {
			familiaBean.crear(familia);
		}catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Crear Exitosa");
		return MOSTRAR_TODOS;
	}

	public String actualizarFamiliaStart() {
		return ACTUALIZAR;
	}
	
	public String actualizarFamiliaEnd() {
		try {
			familiaBean.actualizar(familia);
		} catch(ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Actualizar Exitosa");
		return MOSTRAR_TODOS;
	}
	
	public String eliminarFamiliaStart() {
		return ELIMINAR;
	}
	
	public String eliminarFamiliaEnd() {
		try {
			familiaBean.eliminar(familia);
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
