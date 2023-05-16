import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A custom sliders JPanel that manages the game parameters and displays game statistics
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 */
class Sliders extends JPanel implements ActionListener {

    /** Statistic labels */
    private final JLabel[] labels;

    /** Selected object labels */
    private final JLabel[] objectLabels;

    /** Constructs a Sliders JPanel */
    public Sliders() {
        // Layout
        this.setLayout(new GridLayout(3, 1));

        // Options tabbed pane
        JTabbedPane pane = new JTabbedPane();

        pane.setBorder(new EmptyBorder(10, 100, 0, 100));

        pane.addTab("People", new PersonPane());
        pane.addTab("Grass", new GrassPane());
        pane.addTab("Zombies", new ZombiePane());

        // Statistics panel
        JPanel stats = new JPanel(new GridLayout(4, 1));

        stats.setBorder(new EmptyBorder(50, 0, 150, 0));

        // Stat labels
        this.labels = new JLabel[4];

        this.labels[0] = new JLabel("Generation " + Main.GENERATION, SwingConstants.CENTER);
        this.labels[0].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        this.labels[1] = new JLabel("Humans: " + Main.NUM_PEOPLE, SwingConstants.CENTER);
        this.labels[1].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        this.labels[2] = new JLabel("Zombies: " + Main.NUM_ZOMBIE, SwingConstants.CENTER);
        this.labels[2].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        this.labels[3] = new JLabel("Grass: " + Main.NUM_GRASS, SwingConstants.CENTER);
        this.labels[3].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        // Click select dropdown
        JPanel selectedObject = new JPanel(new GridLayout(2, 1));
        JPanel select = new JPanel();
        JPanel selectedStats = new JPanel(new GridLayout(2, 1));
        selectedStats.setBorder(new EmptyBorder(0, 0, 100, 0));

        this.objectLabels = new JLabel[2];

        this.objectLabels[0] = new JLabel("", JLabel.CENTER);
        this.objectLabels[0].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        this.objectLabels[1] = new JLabel("", JLabel.CENTER);
        this.objectLabels[1].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        selectedStats.add(this.objectLabels[0]);
        selectedStats.add(this.objectLabels[1]);

        JLabel selectLabel = new JLabel("Upon click, add a ", JLabel.CENTER);
        selectLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        JComboBox<ClickActions> dropdown = new JComboBox<>(ClickActions.values());

        dropdown.addActionListener(this);
        dropdown.setSelectedIndex(0);

        select.add(selectLabel);
        select.add(dropdown, JPanel.CENTER_ALIGNMENT);

        selectedObject.add(select);
        selectedObject.add(selectedStats);

        // Adding items to this
        for (JLabel label : this.labels) {
            stats.add(label);
        }

        this.add(pane);
        this.add(stats);
        this.add(selectedObject);

        this.setVisible(true);
    }


    /**
     * refresh
     * Refreshes the game statistics panel and repaints
     */
    public void refresh() {
        this.labels[0].setText("Generation " + Main.GENERATION);
        this.labels[1].setText("Humans: " + Main.NUM_PEOPLE);
        this.labels[2].setText("Zombies: " + Main.NUM_ZOMBIE);
        this.labels[3].setText("Grass: " + Main.NUM_GRASS);

        if (Main.selectedObject == null) {
            this.objectLabels[0].setText("");
            this.objectLabels[1].setText("");
        } else {
            this.objectLabels[0].setText("Selected Object:");
            this.objectLabels[1].setText("Health: " + Math.round(((GameObject) Main.selectedObject).getHealth() * 100.0) / 100.0);
        }


        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<ClickActions> cb = (JComboBox<ClickActions>) e.getSource();
        MatrixDisplayWithMouse.gameObjectToAdd = (ClickActions) cb.getSelectedItem();
    }

    /**
     * The Person tab in the options panel
     * @author Harry Xu
     * @version 1.0 - May 15th 2023
     */
    private static class PersonPane extends JPanel implements ChangeListener {

        /** Life expectancy slider */
        private final JSlider slider1;

        /** Hungry-ness coefficient slider */
        private final JSlider slider2;

        /** Average vision slider */
        private final JSlider slider3;

        public PersonPane() {
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Layout
            GridLayout layout = new GridLayout(3, 1);
            this.setLayout(layout);

            // Slider 1
            JPanel panel1 = new JPanel(new GridLayout(2, 1));

            this.slider1 = new JSlider(JSlider.HORIZONTAL);

            this.slider1.setMajorTickSpacing(20);
            this.slider1.setMinorTickSpacing(2);
            this.slider1.setPaintTicks(true);
            this.slider1.setPaintLabels(true);
            this.slider1.setMaximum(200);

            this.slider1.addChangeListener(this);

            this.slider1.setValue((int) (1.0 / Person.HEALTH_LOSS_RATE));

            JLabel sliderLabel = new JLabel("Average Life Expectancy", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel1.add(sliderLabel);
            panel1.add(this.slider1);

            this.add(panel1);

            // Slider 2
            JPanel panel2 = new JPanel(new GridLayout(2, 1));

            this.slider2 = new JSlider(JSlider.HORIZONTAL);

            this.slider2.setMajorTickSpacing(10);
            this.slider2.setMinorTickSpacing(1);
            this.slider2.setPaintTicks(true);
            this.slider2.setPaintLabels(true);

            this.slider2.addChangeListener(this);

            this.slider2.setValue((int) (Person.HUNGRY_THRESHOLD * 100.0));

            sliderLabel = new JLabel("Hungry-ness Coefficient", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel2.add(sliderLabel);
            panel2.add(this.slider2);

            this.add(panel2);

            // Slider 3
            JPanel panel3 = new JPanel(new GridLayout(2, 1));

            this.slider3 = new JSlider(JSlider.HORIZONTAL);

            this.slider3.setMajorTickSpacing(10);
            this.slider3.setMinorTickSpacing(1);
            this.slider3.setPaintTicks(true);
            this.slider3.setPaintLabels(true);

            this.slider3.addChangeListener(this);

            this.slider3.setValue(Person.AVERAGE_VISION);

            sliderLabel = new JLabel("Average Vision", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel3.add(sliderLabel);
            panel3.add(this.slider3);

            this.add(panel3);

            this.setVisible(true);
        }

        /**
         * stateChanged
         * changes the appropriate game parameter based on which slider is updates
         * @param e the {@link ChangeEvent} object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == this.slider1) {
                Person.HEALTH_LOSS_RATE = 1.0 / this.slider1.getValue();
            }
            if (e.getSource() == this.slider2) {
                Person.HUNGRY_THRESHOLD = this.slider2.getValue() / 100.0;
            }
            if (e.getSource() == this.slider3) {
                Person.AVERAGE_VISION = this.slider3.getValue();
            }
        }
    }

    /**
     * The Grass tab in the options panel
     * @author Harry Xu
     * @version 1.0 - May 15th 2023
     */
    private static class GrassPane extends JPanel implements ChangeListener {

        /** Life expectancy slider */
        private final JSlider slider1;

        /** Hungry-ness coefficient slider */
        private final JSlider slider2;

        /** Average vision slider */
        private final JSlider slider3;

        public GrassPane() {
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Layout
            GridLayout layout = new GridLayout(3, 1);
            this.setLayout(layout);

            // Slider 1
            JPanel panel1 = new JPanel(new GridLayout(2, 1));

            this.slider1 = new JSlider(JSlider.HORIZONTAL);

            this.slider1.setMajorTickSpacing(10);
            this.slider1.setMinorTickSpacing(1);
            this.slider1.setPaintTicks(true);
            this.slider1.setPaintLabels(true);

            this.slider1.addChangeListener(this);

            this.slider1.setValue((int) (Grass.REPRODUCTION_THRESHOLD * 100.0));

            JLabel sliderLabel = new JLabel("Grass Reproduction Rate", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel1.add(sliderLabel);
            panel1.add(this.slider1);

            this.add(panel1);


            // Slider 2
            JPanel panel2 = new JPanel(new GridLayout(2, 1));

            this.slider2 = new JSlider(JSlider.HORIZONTAL);

            this.slider2.setMajorTickSpacing(10);
            this.slider2.setMinorTickSpacing(1);
            this.slider2.setPaintTicks(true);
            this.slider2.setPaintLabels(true);

            this.slider2.addChangeListener(this);

            this.slider2.setValue((int) (Main.NEW_GRASS_THRESHOLD * 100.0));

            sliderLabel = new JLabel("Spawn Rate", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel2.add(sliderLabel);
            panel2.add(this.slider2);

            this.add(panel2);

            // Slider 3
            JPanel panel3 = new JPanel(new GridLayout(2, 1));

            this.slider3 = new JSlider(JSlider.HORIZONTAL);

            this.slider3.setMajorTickSpacing(10);
            this.slider3.setMinorTickSpacing(1);
            this.slider3.setPaintTicks(true);
            this.slider3.setPaintLabels(true);

            this.slider3.addChangeListener(this);

            this.slider3.setValue((int) (Grass.NUTRITIONAL_VALUE_FACTOR * 100.0));

            sliderLabel = new JLabel("Nutritional Value", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel3.add(sliderLabel);
            panel3.add(this.slider3);

            this.add(panel3);

            this.setVisible(true);
        }

        /**
         * stateChanged
         * changes the appropriate game parameter based on which slider is updates
         * @param e the {@link ChangeEvent} object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == this.slider1) {
                Grass.REPRODUCTION_THRESHOLD = this.slider1.getValue() / 100.0;
            }
            if (e.getSource() == this.slider2) {
                Main.NEW_GRASS_THRESHOLD = this.slider2.getValue() / 100.0;
            }
            if (e.getSource() == this.slider3) {
                Grass.NUTRITIONAL_VALUE_FACTOR = this.slider3.getValue() / 100.0;
            }
        }
    }

    /**
     * The Zombie tab in the options panel
     * @author Harry Xu
     * @version 1.0 - May 15th 2023
     */
    private static class ZombiePane extends JPanel implements ChangeListener {

        /** Life expectancy slider */
        private final JSlider slider1;

        /** Hungry-ness coefficient slider */
        private final JSlider slider2;

        /** Average vision slider */
        private final JSlider slider3;

        public ZombiePane() {
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Layout
            GridLayout layout = new GridLayout(3, 1);
            this.setLayout(layout);

            // Slider 1
            JPanel panel1 = new JPanel(new GridLayout(2, 1));

            this.slider1 = new JSlider(JSlider.HORIZONTAL);

            this.slider1.setMajorTickSpacing(20);
            this.slider1.setMinorTickSpacing(2);
            this.slider1.setPaintTicks(true);
            this.slider1.setPaintLabels(true);

            this.slider1.setMaximum(200);

            this.slider1.addChangeListener(this);

            this.slider1.setValue((int) (1.0 / Zombie.HEALTH_LOSS_RATE));

            JLabel sliderLabel = new JLabel("Average Lifespan", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel1.add(sliderLabel);
            panel1.add(this.slider1);

            this.add(panel1);


            // TODO: maybe remove sliders 2 and 3
            // Slider 2
            JPanel panel2 = new JPanel(new GridLayout(2, 1));

            this.slider2 = new JSlider(JSlider.HORIZONTAL);

            this.slider2.setMajorTickSpacing(10);
            this.slider2.setMinorTickSpacing(1);
            this.slider2.setPaintTicks(true);
            this.slider2.setPaintLabels(true);

            this.slider2.addChangeListener(this);

            this.slider2.setValue((int) (Main.NEW_GRASS_THRESHOLD * 100.0));

            sliderLabel = new JLabel("Spawn Rate", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel2.add(sliderLabel);
            panel2.add(this.slider2);

//            this.add(panel2);

            // Slider 3
            JPanel panel3 = new JPanel(new GridLayout(2, 1));

            this.slider3 = new JSlider(JSlider.HORIZONTAL);

            this.slider3.setMajorTickSpacing(10);
            this.slider3.setMinorTickSpacing(1);
            this.slider3.setPaintTicks(true);
            this.slider3.setPaintLabels(true);

            this.slider3.addChangeListener(this);

            this.slider3.setValue((int) (Grass.NUTRITIONAL_VALUE_FACTOR * 100.0));

            sliderLabel = new JLabel("Nutritional Value", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel3.add(sliderLabel);
            panel3.add(this.slider3);

//            this.add(panel3);

            this.setVisible(true);
        }

        /**
         * stateChanged
         * changes the appropriate game parameter based on which slider is updates
         * @param e the {@link ChangeEvent} object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == this.slider1) {
                Zombie.HEALTH_LOSS_RATE = 1.0 / this.slider1.getValue();
            }
            if (e.getSource() == this.slider2) {
                Main.NEW_GRASS_THRESHOLD = this.slider2.getValue() / 100.0;
            }
            if (e.getSource() == this.slider3) {
                Grass.NUTRITIONAL_VALUE_FACTOR = this.slider3.getValue() / 100.0;
            }
        }
    }
}