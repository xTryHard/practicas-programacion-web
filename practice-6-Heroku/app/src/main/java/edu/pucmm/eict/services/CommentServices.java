package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.Comment;

public class CommentServices extends DatabaseOrmHandler<Comment> {

  private static CommentServices instance = null;

  private CommentServices() {
      super(Comment.class);
  }
  
  public static CommentServices getInstance() {
      if (instance == null) {
          instance = new CommentServices();
      }
      return instance;
  }
}