package ua.nure.kn.muzyka.usermanagement.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.muzyka.usermanagement.User;
import ua.nure.kn.muzyka.usermanagement.db.DatabaseException;
import ua.nure.kn.muzyka.usermanagement.util.Messages;

public class EditPanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JTextField firstNameField;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private JButton okButton;
    private JButton cancelButton;
    private Color bgColor = Color.WHITE;
    User user;

    public EditPanel(MainFrame mainFrame) {
        parent = mainFrame;
        user = new User();
        initialize();
    }

    private void initialize() {
        this.setName("EditPanel"); //$NON-NLS-1$
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    public void fillFields(User user) {
        this.user = user;
        this.getFirstNameField().setText(user.getFirstName());
        this.getLastNameField().setText(user.getLastName());
        this.getDateOfBirthField().setText(new java.text.SimpleDateFormat("dd.MM.yyyy") //$NON-NLS-1$
                .format(user.getDateOfBirth()));
    }

    private JPanel getFieldPanel() {
        if(fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, ua.nure.kn.muzyka.usermanagement.gui.Messages.getString("EditPanel.firstName"), getFirstNameField());  //$NON-NLS-1$
            addLabeledField(fieldPanel, ua.nure.kn.muzyka.usermanagement.gui.Messages.getString("EditPanel.lastName"), getLastNameField());  //$NON-NLS-1$
            addLabeledField(fieldPanel, ua.nure.kn.muzyka.usermanagement.gui.Messages.getString("EditPanel.dateOfBirth"), getDateOfBirthField());  //$NON-NLS-1$
        }
        return fieldPanel;
    }
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }


    private JTextField getFirstNameField() {
        if(firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField"); //$NON-NLS-1$
        }
        return firstNameField;
    }
    private JTextField getDateOfBirthField() {
        if(dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
        }
        return dateOfBirthField;
    }
    private JTextField getLastNameField() {
        if(lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField"); //$NON-NLS-1$
        }
        return lastNameField;
    }


    private JButton getOkButton() {
        if (okButton == null){
            okButton = new JButton();
            okButton.setText(ua.nure.kn.muzyka.usermanagement.gui.Messages.getString("EditPanel.saveButton"));  //$NON-NLS-1$
            okButton.setName("okButton"); //$NON-NLS-1$
            okButton.setActionCommand("ok"); //$NON-NLS-1$
            okButton.addActionListener(this);
        }
        return okButton;
    }
    private JButton getCancelButton() {
        if (cancelButton == null){
            cancelButton = new JButton();
            cancelButton.setText(ua.nure.kn.muzyka.usermanagement.gui.Messages.getString("EditPanel.cancelButton"));  //$NON-NLS-1$
            cancelButton.setName("cancelButton"); //$NON-NLS-1$
            cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("ok".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();

            try {
                user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e2) {
                JOptionPane.showMessageDialog(this, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }
    private void clearFields() {
        getFirstNameField().setText(""); //$NON-NLS-1$
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText(""); //$NON-NLS-1$
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText(""); //$NON-NLS-1$
        getDateOfBirthField().setBackground(bgColor);
    }


    public void setUser(User user) {
    }
}