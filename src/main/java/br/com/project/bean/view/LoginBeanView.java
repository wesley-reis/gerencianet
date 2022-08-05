package br.com.project.bean.view;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagedViewAbstract;
import br.com.project.geral.controller.SessionController;
import br.com.srv.interfaces.SrvLogin;

@Controller
@Scope(value = "request")
@ManagedBean(name = "loginBeanView")
public class LoginBeanView extends BeanManagedViewAbstract {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	
	@Resource
	private SessionController sessionController;
	
	@Resource
	private SrvLogin srvLogin;
	
	
	@RequestMapping(value = "**/invalidar_session", method = RequestMethod.POST)
	public void invalidarSessionMetodo(HttpServletRequest httpServletRequest) throws Exception {
		String userLogadoSesssao = null;
		if (httpServletRequest.getUserPrincipal() != null) {
			userLogadoSesssao = httpServletRequest.getUserPrincipal().getName();
		}
		
		if (userLogadoSesssao == null || (userLogadoSesssao != null && userLogadoSesssao.trim().isEmpty())) {
			userLogadoSesssao = httpServletRequest.getRemoteUser();
		}
		
		if (userLogadoSesssao != null && !userLogadoSesssao.isEmpty()) {
			sessionController.invalidateSession(userLogadoSesssao);
		}
	}

	public void invalidar(ActionEvent actionEvent) throws Exception {
		RequestContext context = RequestContext.getCurrentInstance();
		
		FacesMessage message = null;
		boolean loggedIn = false;
		
		if (srvLogin.autentico(getUsername(), getPassword())) {
			sessionController.invalidateSession(getUsername());
			loggedIn = true;
		}else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso negado", "Login ou senha incorreto.");
		}
		
		if (message != null) {
			FacesContext.getCurrentInstance().addMessage("msg", message);
		}
		
		context.addCallbackParam("loggedIn", loggedIn);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected Class<?> getClassImplement() {
		return null;
	}

	@Override
	protected InterfaceCrud<?> getController() {
		return null;
	}

}
