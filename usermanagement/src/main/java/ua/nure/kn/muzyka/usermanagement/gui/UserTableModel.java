package ua.nure.kn.muzyka.usermanagement.gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.kn.muzyka.usermanagement.User;

public class UserTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"ID", Messages.getString("UserTableModel.firstName"), Messages.getString("UserTableModel.lastName")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};
    private List users = null;

    public UserTableModel(Collection users) {
        this.users = new ArrayList(users);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        // TODO Auto-generated method stub
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = (User) users.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    public User getUser(int index) {
        return (User) users.get(index);
    }

    public void addUsers(Collection users) {
        this.users.addAll(users);

    }

    public void clearUsers() {
        this.users = new ArrayList();
    }}