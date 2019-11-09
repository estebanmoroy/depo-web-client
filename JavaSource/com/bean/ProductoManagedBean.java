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
	
	private List<Producto> listaProductos;
	
	public Producto getProducto() {
		if(producto == null) {
			producto = new Producto();
		}
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public List<Producto> getListaProductos() {
		if(listaProductos == null) {
			listaProductos = ordenarPorNombre(productoBean.obtenerTodos());
		}
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	public String obtenerTodosLosProductos(){
		setListaProductos(ordenarPorNombre(productoBean.obtenerTodos()));
		return QUEDARSE;
	}
	
	public String obtenerListaPorNombre(String filtro){
		setListaProductos(ordenarPorNombre(productoBean.obtenerTodosPorNombre(filtro)));
		return QUEDARSE;
	}
	
	private List<Producto> ordenarPorNombre(List<Producto> lista){
		Collections.sort(lista, new Comparator<Producto>() {
			@Override
			public int compare(Producto p1, Producto p2) {
				return p1.getNombre().compareTo(p2.getNombre());
			}
		});
		return lista;
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
