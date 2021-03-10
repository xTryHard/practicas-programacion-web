

package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.User;


public class UserServices extends DatabaseOrmHandler<User>{

    private static UserServices instance = null;

    private UserServices() {
        super(User.class);
    }

    public static UserServices getInstance() {
        if (instance == null) {
            instance = new UserServices();
        }
        return instance;
    }

    // public boolean createUser(User user) {
    //     boolean ok = false;

    //     Connection conn = null;
    //     try {

    //         String query = "insert into user(username, name, password) values(?,?,?)";
    //         conn = DataBaseConnServices.getInstance().getConn();
    //         //
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         //Antes de ejecutar seteo los parametros.
    //         prepareStatement.setString(1, user.getUserName());
    //         prepareStatement.setString(2, user.getName());
    //         prepareStatement.setString(3, user.getPassword());
    //         int fila = prepareStatement.executeUpdate();
    //         ok = fila > 0 ;

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
    //         System.out.println(ex.toString());
    //     } finally{
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return ok;
    // }

    // public List<User> getAllUsers() {
    //     List<User> users = new ArrayList<>();
    //     Connection conn = null;
    //     try {
    //         //
    //         String query = "select * from User;";
    //         conn = DataBaseConnServices.getInstance().getConn(); 
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         ResultSet rs = prepareStatement.executeQuery();
    //         while(rs.next()){
    //             User user = new User();
    //             user.setName(rs.getString("name"));
    //             user.setUserName(rs.getString("username"));
    //             user.setPassword(rs.getString("password"));
    //             users.add(user);
    //         }

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
    //         System.out.println(ex.toString());
    //     } finally{
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return users;
    // }

    // public boolean existsUser(String username) {
    //     boolean exists = false;

    //     Connection conn = null;
    //     try {
    //         //
    //         String query = "select * from User Where username = ?;";
    //         conn = DataBaseConnServices.getInstance().getConn(); 
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         prepareStatement.setString(1, username);
    //         ResultSet rs = prepareStatement.executeQuery();
    //         exists = rs.next();

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
    //         System.out.println(ex.toString());
    //     } finally{
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return exists;
    // }


}