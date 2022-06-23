package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;


@SuppressWarnings("unchecked")
public class ViewScope implements Scope, Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if( instance == null) {
			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}
		return instance;
	}

	@Override
	public String getConversationId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes  = new FacesRequestAttributes(facesContext);
		
		return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		
		Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		
		if (callBacks != null) {
			callBacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
	}

	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);
		if(instance != null) {
			
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			
			if (callBacks != null) {
				callBacks.remove(name);
			}
			return null;
		}
		return null;
	}

	@Override
	public Object resolveContextualObject(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		return facesRequestAttributes.resolveReference(name);
	}
	
	//getViewRoot()
	//Retorna o componente raiz que est� associado a esta solicita��o(request).
	//getViewMap retorna um Map que atua como a interface para o armazenamento de dados

	private Map<String, Object> getViewMap(){
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<String, Object>();
	}

}
