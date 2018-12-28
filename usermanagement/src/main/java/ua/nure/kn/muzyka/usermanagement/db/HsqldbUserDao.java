package ua.nure.kn.muzyka.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.muzyka.usermanagement.User;

class HsqldbUserDao implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private static final String FIND_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
    private static final String SELECT_BY_NAME  = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname=? AND lastname=?";
    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        Connection connetion = connectionFactory.createConnection();
        try {
            PreparedStatement statement = connetion.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int n = statement.executeUpdate();

            if (n != 1) {
                throw new DatabaseException("Number of the inserted rows: " + n);
            }

            CallableStatement callableStatement = connetion.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();

            while (keys.next()) {
                user.setId(new Long(keys.getLong(1)));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connetion.close();
            return user;

        } catch (DatabaseException e) {
            throw e;

        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    @Override
    public void update(User user) throws DatabaseException {

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());

            int n = statement.executeUpdate();

            if (n != 1) {
                throw new DatabaseException("Number of the updated rows: " + n);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DatabaseException();
        }

    }

    @Override
    public void delete(User user) throws DatabaseException {

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

            statement.setLong(1, user.getId());

            int n = statement.executeUpdate();

            if (n != 1) {
                throw new DatabaseException("Number of the deleted rows: " + n);
            }

            statement.close();
            connection.close();

        } catch (DatabaseException e) {
            throw e;

        } catch (SQLException e) {

            throw new DatabaseException(e);
        }

    }

    @Override
    public User find(Long id) throws DatabaseException {

        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = new User();

            while (resultSet.next()) {

                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));

            }
            resultSet.close();
            statement.close();
            connection.close();
            return user;

        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {

            throw new DatabaseException(e);
        }

    }

    @Override
    public Collection findAll() throws DatabaseException {
        Collection result = new LinkedList();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

            while (resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));

                result.add(user);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {

            throw new DatabaseException(e);
        }

        return result;
    }

    public Collection find(String firstName, String lastName) throws DatabaseException {
        Collection<User> users = new LinkedList<>();

        try {Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4)
                );
                users.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException("Database has some errors", e);
        }

        return users;
    }

}