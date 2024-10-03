package org.arle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.arle.entity.Empleado;

import java.util.List;

public class EmpleadoRepository {

    private final EntityManagerFactory emf;

    public EmpleadoRepository() {

        emf = Persistence.createEntityManagerFactory("productoPU");
    }

    public void crear(Empleado empleado) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Empleado leer(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public List<Empleado> leerTodos() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Empleado p", Empleado.class)
                    .getResultList();
        }
    }

    public void actualizar(Empleado empleado) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(empleado);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Empleado empleado = em.find(Empleado.class, id);
            if (empleado != null) {
                em.remove(empleado);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        emf.close();
    }
}