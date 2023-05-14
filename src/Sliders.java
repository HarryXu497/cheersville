import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class Sliders extends JPanel {

    private final JLabel[] labels;

    public Sliders() {
        this.setLayout(new GridLayout(2, 1));

        JTabbedPane pane = new JTabbedPane();

        pane.setBorder(new EmptyBorder(10, 200, 0, 200));

        pane.addTab("People", new PersonPane());
        pane.addTab("Grass", new GrassPane());

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

        for (JLabel label : this.labels) {
            stats.add(label);
        }

        this.add(pane);
        this.add(stats);

        this.setVisible(true);
    }


    /**
     * refresh
     * Refresh the slider panel;
     */
    public void refresh() {
        this.labels[0].setText("Generation " + Main.GENERATION);
        this.labels[1].setText("Humans: " + Main.NUM_PEOPLE);
        this.labels[2].setText("Zombies: " + Main.NUM_ZOMBIE);
        this.labels[3].setText("Grass: " + Main.NUM_GRASS);

        this.repaint();
    }

    private class PersonPane extends JPanel implements ChangeListener {

        private final JSlider slider1;
        private final JSlider slider2;
        private final JSlider slider3;

        public PersonPane() {
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Layout
            GridLayout layout = new GridLayout(0, 1);
            this.setLayout(layout);

            // Slider 1
            this.slider1 = new JSlider(JSlider.HORIZONTAL);

            this.slider1.setMajorTickSpacing(10);
            this.slider1.setMinorTickSpacing(1);
            this.slider1.setPaintTicks(true);
            this.slider1.setPaintLabels(true);

            this.slider1.addChangeListener(this);

            JLabel sliderLabel = new JLabel("Average Life Expectancy", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.add(sliderLabel);

            this.slider1.setValue((int) (1.0 / Person.HEALTH_LOSS_RATE));

            this.add(this.slider1);

            // Slider 2
            this.slider2 = new JSlider(JSlider.HORIZONTAL);

            this.slider2.setMajorTickSpacing(10);
            this.slider2.setMinorTickSpacing(1);
            this.slider2.setPaintTicks(true);
            this.slider2.setPaintLabels(true);

            this.slider2.addChangeListener(this);

            this.slider2.setValue((int) (Person.HUNGRY_THRESHOLD * 100.0));

            sliderLabel = new JLabel("Hungry-ness Coefficient", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.add(sliderLabel);

            this.add(this.slider2);

            // Slider 3
            this.slider3 = new JSlider(JSlider.HORIZONTAL);

            this.slider3.setMajorTickSpacing(10);
            this.slider3.setMinorTickSpacing(1);
            this.slider3.setPaintTicks(true);
            this.slider3.setPaintLabels(true);

            this.slider3.addChangeListener(this);

            this.slider3.setValue(Person.AVERAGE_VISION);

            sliderLabel = new JLabel("Average Vision", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.add(sliderLabel);

            this.add(this.slider3);


            this.setVisible(true);
        }

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

    private class GrassPane extends JPanel implements ChangeListener {

        private final JSlider slider1;
        private final JSlider slider2;
        private final JSlider slider3;

        public GrassPane() {
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Layout
            GridLayout layout = new GridLayout(0, 1);
            this.setLayout(layout);

            // Slider 1
            this.slider1 = new JSlider(JSlider.HORIZONTAL);

            this.slider1.setMajorTickSpacing(10);
            this.slider1.setMinorTickSpacing(1);
            this.slider1.setPaintTicks(true);
            this.slider1.setPaintLabels(true);

            this.slider1.addChangeListener(this);

            JLabel sliderLabel = new JLabel("Grass Reproduction Rate", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.slider1.setValue((int) (Grass.REPRODUCTION_THRESHOLD * 100.0));

            this.add(sliderLabel);
            this.add(this.slider1);

            // Slider 2
            this.slider2 = new JSlider(JSlider.HORIZONTAL);

            this.slider2.setMajorTickSpacing(10);
            this.slider2.setMinorTickSpacing(1);
            this.slider2.setPaintTicks(true);
            this.slider2.setPaintLabels(true);

            this.slider2.addChangeListener(this);

            this.slider2.setValue((int) (Main.NEW_GRASS_THRESHOLD * 100.0));

            sliderLabel = new JLabel("Spawn Rate", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


            this.add(sliderLabel);
            this.add(this.slider2);

            // Slider 3
            this.slider3 = new JSlider(JSlider.HORIZONTAL);

            this.slider3.setMajorTickSpacing(10);
            this.slider3.setMinorTickSpacing(1);
            this.slider3.setPaintTicks(true);
            this.slider3.setPaintLabels(true);

            this.slider3.addChangeListener(this);

            this.slider3.setValue((int) (Grass.NUTRITIONAL_VALUE_FACTOR * 100.0));

            sliderLabel = new JLabel("Nutritional Value", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


            this.add(sliderLabel);
            this.add(this.slider3);

            this.setVisible(true);
        }

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
}