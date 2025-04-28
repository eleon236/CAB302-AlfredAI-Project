package com.example.cab302week4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Main class
public class AlfredLoginUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlfredLoginUI().createUI());
    }

    //Weclome to AlfredAI Window
    public void createUI() {
        JFrame frame = new JFrame("Alfred AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel (Image + Title)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel bearLabel = new JLabel();

        // Bear image
        ImageIcon bearIcon = new ImageIcon("img\\Bear.png"); // Update path if needed
        Image scaledImage = bearIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);
        bearLabel.setIcon(new ImageIcon(scaledImage));
        topPanel.add(bearLabel);

        // Main Title
        JLabel title = new JLabel("<html><div style='font-size:35px;'>Welcome to<br><b style='font-size:40px;'>Alfred AI !</b></div></html>");
        title.setFont(new Font("SansSerif", Font.PLAIN, 40));
        topPanel.add(title);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Login subtitle
        JLabel adventureLabel = new JLabel("Ready to continue your adventure?");
        adventureLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(adventureLabel, gbc);

        gbc.gridwidth = 1;

        //Username Box
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(15 );
        gbc.gridx = 1;
        formPanel.add(userField, gbc);

        //Password Box
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);

        //Contiune Button
        JButton continueButton = new JButton("Continue !");
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(continueButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        continueButton.addActionListener(e -> {
            // Open the second window
            new AdventureWindow();
            // Close the main window
            frame.dispose();
        });


        // Bottom Panel (Register link)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel registerLabel = new JLabel("<html>Not an adventurer yet? <a href='#'>Register</a></html>");
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegistrationWindow();  // Open new window
                frame.dispose();           // Close current login window
            }

        });
        bottomPanel.add(registerLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}

//Registration Window
class RegistrationWindow {
    public RegistrationWindow() {
        JFrame regFrame = new JFrame("Register - Start Your Adventure!");
        regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame.setSize(800, 600);
        regFrame.setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // More padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Page Title
        JLabel titleLabel = new JLabel("Start your Adventure !");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Reset after title

        // Username box
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(userField, gbc);

        // Password box
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passField, gbc);

        // Confirm Password box
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(confirmPassLabel, gbc);

        JPasswordField confirmPassField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(confirmPassField, gbc);

        // Go Button
        JButton goButton = new JButton("Go !");
        goButton.setFont(new Font("SansSerif", Font.BOLD, 22));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(goButton, gbc);

        regFrame.setContentPane(mainPanel);
        regFrame.setVisible(true);

        // Go Button Action
        goButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(regFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(regFrame, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(regFrame, "Registration successful for " + username + "!");
                // Save user info or move to next screen if needed
            }
        });
    }
}

// Adventure Window (First page when logged in)
class AdventureWindow {
    public AdventureWindow() {
        JFrame frame = new JFrame("Alfred AI - Adventure Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel with Bear and Titles
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Bear Image Placeholder
        JLabel bearLabel = new JLabel();
        bearLabel.setPreferredSize(new Dimension(150, 200));
        bearLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bearLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Load the image
        ImageIcon icon = new ImageIcon("img\\Bear.png");

        // Resize image if needed
        Image image = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);

        // Set the image to the existing label
        bearLabel.setIcon(icon);

        // Add bearLabel to your panel (or frame)
        topPanel.add(bearLabel); // or frame.add(bearLabel);


        // Title and Welcome Message
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Alfred AI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome back!<br>How will you continue your adventure?</div></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        textPanel.add(titleLabel);
        textPanel.add(welcomeLabel);

        topPanel.add(bearLabel, BorderLayout.WEST);
        topPanel.add(textPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Subjects and New Quest
        buttonPanel.add(createCircleButton("Subject 1"));
        buttonPanel.add(createCircleButton("Subject 2"));
        buttonPanel.add(createCircleButton("Subject 3"));
        buttonPanel.add(createCircleButton("Subject 4"));
        buttonPanel.add(createCircleButton("+\nNew Quest"));

        // Empty panel to center the buttons nicely
        buttonPanel.add(new JLabel());

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
            }

     // CricleButton command
    private JButton createCircleButton(String text) {
        RoundButton button = new RoundButton("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
        button.setPreferredSize(new Dimension(120, 120));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setOpaque(false);
        button.setBackground(Color.LIGHT_GRAY);
        return button;
    }




}





class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillOval(0, 0, getSize().width-1, getSize().height-1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getSize().width-1, getSize().height-1);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = getWidth() / 2;
        return (Math.pow(x - radius, 2) + Math.pow(y - radius, 2)) <= Math.pow(radius, 2);
    }
}
