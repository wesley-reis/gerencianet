package br.com.project.geral.controller;

import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;


@ApplicationScoped
public class SessionControllerImpl implements SessionController {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, HttpSession> hashMap = new HashMap<String, HttpSession>();

	@Override
	public void addSession(String keyLoginUse, HttpSession httpSession) {
		hashMap.put(keyLoginUse, httpSession);
		
	}

	@Override
	public void invalidateSession(String keyLoginUse) {
		HttpSession session = hashMap.get(keyLoginUse);
		
		if(session != null) {
			
			try {
				session.invalidate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("não tem usuario");
		}
		
		hashMap.remove(keyLoginUse);

	}

}
