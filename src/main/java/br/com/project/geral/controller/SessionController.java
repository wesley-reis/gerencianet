package br.com.project.geral.controller;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;


@ApplicationScoped
public interface SessionController extends Serializable {

	void addSession(String keyLoginUse, HttpSession httpSession);
	
	void invalidateSession(String keyLoginUse);
}
