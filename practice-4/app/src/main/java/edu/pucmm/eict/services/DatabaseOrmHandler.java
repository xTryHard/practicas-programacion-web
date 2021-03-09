package edu.pucmm.eict.services;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;



public class DatabaseOrmHandler<T> {
  
    private static EntityManagerFactory emf;
    private Class<T> entityClass;


    public DatabaseOrmHandler(Class<T> entityClass) {
        
        emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager(){
      return emf.createEntityManager();
    }

        /**
     * Metodo para obtener el valor del campo anotado como @ID.
     * @param entidad
     * @return
     */
    private Object getFieldValue(T entity){
      if(entity == null){
          return null;
      }
      //aplicando la clase de reflexión.
      for(Field f : entity.getClass().getDeclaredFields()) {  //tomando todos los campos privados.
          if (f.isAnnotationPresent(Id.class)) { //preguntando por la anotación ID.
              try {
                  f.setAccessible(true);
                  Object valorCampo = f.get(entity);

                  System.out.println("Nombre del campo: "+f.getName());
                  System.out.println("Tipo del campo: "+f.getType().getName());
                  System.out.println("Valor del campo: "+valorCampo );

                  return valorCampo;
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }
      return null;
  }

  public T create(T entity) throws IllegalArgumentException, EntityExistsException, PersistenceException{
    EntityManager em = getEntityManager();

    try {

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

    }finally {
        em.close();
    }
    return entity;
  }

  public T update(T entity) throws PersistenceException{
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    try {
        em.merge(entity);
        em.getTransaction().commit();
    }finally {
        em.close();
    }
    return entity;
  }

  public boolean delete(Object entityId) throws PersistenceException{
    boolean ok = false;
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    try {
        T entidad = em.find(entityClass, entityId);
        em.remove(entidad);
        em.getTransaction().commit();
        ok = true;
    }finally {
        em.close();
    }
    return ok;
  }

  public T find(Object id) throws PersistenceException {
    EntityManager em = getEntityManager();
    try{
        return em.find(entityClass, id);
    } finally {
        em.close();
    }
  }

  public List<T> findAll() throws PersistenceException {
    EntityManager em = getEntityManager();
    try{
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
    } finally {
        em.close();
    }
  }

}