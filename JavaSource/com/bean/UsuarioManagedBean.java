package com.bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.entities.Usuario;
import com.exception.ServicesException;
import com.services.UsuarioBeanRemote;

@ManagedBean
@SessionScoped
public class UsuarioManagedBean {

	private static final String PAGINA_PRINCIPAL = "main";
	private static final String LOGIN = "login";
	private static final String QUEDARSE = null;
	
	private String nombreAcceso;
	private String contrasena;
	
	private Usuario usuarioLogueado;
	
	@EJB
	private UsuarioBeanRemote usuarioBean;
	
	
	public String autenticar() {
		try {
			if(usuarioBean.autenticar(nombreAcceso, contrasena)) {
				return PAGINA_PRINCIPAL;
			}
		} catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
		}
		return QUEDARSE;
	}
	
	public boolean esOperador() {
		if (getUsuarioLogueado().getPerfil().getId() == 3){
			return true;
		}else {
			return false;
		}
	}
	
	public Usuario getUsuarioLogueado() {
		try {
			if (usuarioBean.autenticar(nombreAcceso, contrasena)) {
				usuarioLogueado = usuarioBean.getObjetoUsuario(nombreAcceso, contrasena);
			}
		} catch (ServicesException e) {
			enviarMensajeDeErrorAlUsuario(e.getMessage());
		}
		return usuarioLogueado;
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return LOGIN;
	}
	
	private void enviarMensajeDeErrorAlUsuario(String mensaje) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));
	}
	
	private FacesContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}
	
	private HttpServletRequest getRequest() {
		  return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public String getNombreAcceso() {
		return nombreAcceso;
	}

	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}
