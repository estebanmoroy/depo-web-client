package com.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.entities.Producto;
import com.exception.ServicesException;
import com.services.ProductoBeanRemote;

@ManagedBean
@RequestScoped
public class ProductoManagedBean {

	@EJB
	private ProductoBeanRemote productoBean;
	
	private static final String MOSTRAR_TODOS = "mostrarTodosLosProductos";
	private static final String DETALLES = "verDetallesProducto";
	private static final String CREAR = "crearProducto";
	private static final String ACTUALIZAR = "actualizarProducto";
	private static final String	ELIMINAR = "eliminarProducto";
	private static final String QUEDARSE = null;
	
	private Producto producto;
	
	public Producto getProducto() {
		if(producto == null) {
			producto = new Producto();
		}
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public List<Producto> obtenerTodosLosProductos(){
		return productoBean.obtenerTodos();
	}
	
	public List<Producto> obtenerListaPorCodigo(String filtro){
		//TODO
		return null;
	}
	
	public List<Producto> obtenerListaPorNombre(String filtro){
		//TODO
		return null;
	}
	
	public List<Producto> obtenerListaPorDescripcion(String filtro){
		//TODO
		return null;
	}
	
	public Producto obtenerProductoPorCodigo() {
		//TODO
		return producto;
	}
	
	public String verDetallesProducto() {
		return DETALLES;
	}
	
	public String crearProductoStart() {
		return CREAR;
	}
	
	public String crearProductoEnd() {
		try {
			productoBean.crear(producto);
		}catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Crear Exitosa");
		return MOSTRAR_TODOS;
	}

	public String actualizarProductoStart() {
		return ACTUALIZAR;
	}
	
	public String actualizarProductoEnd() {
		try {
			productoBean.actualizar(producto);
		} catch(ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
			return QUEDARSE;
		}
		enviarMensajeAlUsuario("Operación Actualizar Exitosa");
		return MOSTRAR_TODOS;
	}
	
	public String eliminarProductoStart() {
		return ELIMINAR;
	}
	
	public String eliminarProductoEnd() {
		try {
			productoBean.eliminar(producto);
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
