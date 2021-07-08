package com.example.icm_proyectofinal;

public class Cita {
    int id;
    String dia;
    String hora;
    String nombre;
    int telefono;
    String cobro;
    String metodo_p;
    String comentarios;

    public Cita() {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.nombre = nombre;
        this.telefono = telefono;
        this.cobro = cobro;
        this.metodo_p = metodo_p;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCobro() {
        return cobro;
    }

    public void setCobro(String cobro) {
        this.cobro = cobro;
    }

    public String getMetodo_p() {
        return metodo_p;
    }

    public void setMetodo_p(String metodo_p) {
        this.metodo_p = metodo_p;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
