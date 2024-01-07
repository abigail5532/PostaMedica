/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import Controladores.HistoriasJpaController;
import Entidades.Historias;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandra
 */
@ManagedBean
@RequestScoped
public class BeanHistorias {
    private int idlista;
    private String nrohistlista;
    private String nroboletalista;
    private String fecreglista;
    private char activelista;
    
    public BeanHistorias() {}
    //Get and Set
    public int getIdlista() {
        return idlista;
    }

    public void setIdlista(int idlista) {
        this.idlista = idlista;
    }

    public String getNrohistlista() {
        return nrohistlista;
    }

    public void setNrohistlista(String nrohistlista) {
        this.nrohistlista = nrohistlista;
    }

    public String getNroboletalista() {
        return nroboletalista;
    }

    public void setNroboletalista(String nroboletalista) {
        this.nroboletalista = nroboletalista;
    }

    public String getFecreglista() {
        return fecreglista;
    }

    public void setFecreglista(String fecreglista) {
        this.fecreglista = fecreglista;
    }

    public char getActivelista() {
        return activelista;
    }

    public void setActivelista(char activelista) {
        this.activelista = activelista;
    }
    
    //Métodos    
    //Método para listar datos
    public List<Historias> tablaHistorias(){
        try{
            HistoriasJpaController ctrl = new HistoriasJpaController();
            List<Historias> list = ctrl.tablaHistorias();
            return list;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR no se puede listar los datos" + ex); 
        }
        return null;
    }
    
    //Método para agregar datos
    public void agregarHistorias(){
        try{
            // Generar un número de 4 dígitos de forma aleatoria
            if (nrohistlista == null || nrohistlista.isEmpty()) {
                Random rand = new Random();
                int numeroAleatorio = rand.nextInt(9000) + 1000; 
                nrohistlista = String.valueOf(numeroAleatorio); // Almacenar el número aleatorio en la variable de instancia
            }
            HistoriasJpaController ctrl = new HistoriasJpaController();
            ctrl.create(new Historias(idlista,nrohistlista,nroboletalista,fecreglista,'A'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede agregar los datos" + ex);   
        }
    }
    
    //Método para editar datos
    public void actualizarHistorias(){
        try{
            HistoriasJpaController ctrl = new HistoriasJpaController();
            // Asegurarse de que nrohistlista no sea nulo antes de intentar actualizar
            if (nrohistlista != null && !nrohistlista.isEmpty()) {
                ctrl.editLista(new Historias(idlista, nrohistlista, nroboletalista, fecreglista, 'A'));
            } else {
                JOptionPane.showMessageDialog(null, "ERROR: nrohistlista no puede ser nulo o vacío");
            }
        }catch(Exception ex){
          JOptionPane.showMessageDialog(null, "ERROR no se puede actualizar los datos" + ex);  
        }
    }
    
    //Método para eliminar datos
    public void eliminarHistorias(Historias historias){
        try{
            HistoriasJpaController ctrl = new HistoriasJpaController();
            ctrl.editLista(new Historias(historias.getIdlista(),historias.getNrohistlista(),historias.getNroboletalista(),historias.getFecreglista(),'N'));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR no se puede eliminar los datos" + ex);   
        }
    }
    
    //Método para solicitar la fecha al sistema
    @PostConstruct
    public void init() {
        // Obtener la fecha actual del sistema y asignarla al atributo fechaingpac
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecreglista = dateFormat.format(new Date());
    }
}
