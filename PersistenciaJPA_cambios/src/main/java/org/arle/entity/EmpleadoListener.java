package org.arle.entity;

import jakarta.persistence.*;

public class EmpleadoListener {

    @PrePersist
    public void prePersist(Empleado empleado) {
        System.out.println("Empleado a ser persistido: " + empleado);
    }

    @PostPersist
    public void postPersist(Empleado empleado) {
        System.out.println("Empleado persistido: " + empleado);
    }

    @PreUpdate
    public void preUpdate(Empleado empleado) {
        System.out.println("Empleado a ser actualizado: " + empleado);
    }

    @PostUpdate
    public void postUpdate(Empleado empleado) {
        System.out.println("Empleado actualizado: " + empleado);
    }

    @PreRemove
    public void preRemove(Empleado empleado) {
        System.out.println("Empleado a ser eliminado: " + empleado);
    }

    @PostRemove
    public void postRemove(Empleado empleado) {
        System.out.println("Empleado eliminado: " + empleado);
    }
}
