package com.piantino.controller;

import com.piantino.ejb.MenuFacadeLocal;
import com.piantino.model.Menu;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "menuController")
@ViewScoped
public class MenuController implements Serializable {

    @EJB
    private MenuFacadeLocal menuEJB;
    private List<Menu> items;

    private MenuModel model;

    @PostConstruct
    public void init() {

        listarMenus();
        model = new DefaultMenuModel();
        establecerPermisos();
    }

    public void listarMenus() {
        try {
            items = menuEJB.findAll();
        } catch (Exception e) {

        }
    }

    public void establecerPermisos() {

        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        for (Menu m : items) {
            if ((m.esSuperUsuario() && us.esSuperUsuario()) || !m.esSuperUsuario()) {
                if (m.getTipo().equals("S") && m.getSubmenu() == null) {
                    //DefaultSubMenu primerSubmenu = DefaultSubMenu.builder().build();
                    //primerSubmenu.setLabel(m.getNombre());
                    DefaultSubMenu primerSubmenu = DefaultSubMenu.builder()
                            .label(m.getNombre())
                            .build();
                    for (Menu i : items) {
                        Menu submenu = i.getSubmenu();
                        if (submenu != null && !i.getTipo().equals("A")) {
                            if (submenu.getId() == m.getId()) {
                                if (i.getTipo().equals("S")) {
                                    //DefaultSubMenu segundoSubmenu = DefaultSubMenu.builder().build();
                                    //segundoSubmenu.setLabel(i.getNombre());
                                    DefaultSubMenu segundoSubmenu = DefaultSubMenu.builder()
                                            .label(i.getNombre())
                                            .build();

                                    for (Menu g : items) {
                                        Menu submenu2 = g.getSubmenu();
                                        if (submenu2 != null && !g.getTipo().equals("A")) {
                                            if (submenu2.getId() == i.getId()) {
                                                if ((g.esSuperUsuario() && us.esSuperUsuario()) || !g.esSuperUsuario()) {
                                                    //DefaultMenuItem item = DefaultMenuItem.builder().build();
                                                    //item.setValue(g.getNombre());
                                                    DefaultMenuItem item = DefaultMenuItem.builder()
                                                            .value(g.getNombre())
                                                            .url(g.getUrl())
                                                            .build();
                                                    segundoSubmenu.getElements().add(item);
                                                }
                                            }
                                        }
                                    }
                                    primerSubmenu.getElements().add(segundoSubmenu);
                                } else {
                                    if ((i.esSuperUsuario() && us.esSuperUsuario()) || !i.esSuperUsuario()) {
                                        //DefaultMenuItem item = DefaultMenuItem.builder().build();
                                        //item.setValue(i.getNombre());
                                        DefaultMenuItem item = DefaultMenuItem.builder()
                                                .value(i.getNombre())
                                                .url(i.getUrl())
                                                .build();
                                        primerSubmenu.getElements().add(item);
                                    }
                                }
                            }
                        }
                    }
                    model.getElements().add(primerSubmenu);
                }//aca
                else {
                    if (m.getSubmenu() == null && !m.getTipo().equals("A")) {
                        //DefaultMenuItem item =DefaultMenuItem.builder().build();
                        //item.setValue(m.getNombre());
                        DefaultMenuItem item = DefaultMenuItem.builder()
                                .value(m.getNombre())
                                .url(m.getUrl())
                                .build();
                        model.getElements().add(item);
                    }
                }
            }
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

}
