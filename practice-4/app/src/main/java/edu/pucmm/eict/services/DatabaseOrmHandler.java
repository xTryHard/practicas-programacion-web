package edu.pucmm.eict.services;

import javax.persistence.*;



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
}