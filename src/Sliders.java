import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class Sliders extends JPanel {
    public Sliders() {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("People", new PersonPane());
        pane.addTab("Grass", new GrassPane());

        this.add(pane);

        this.setVisible(true);
    }

    private class PersonPane extends JPanel implements ChangeListener {

        private final JSlider slider1;
        private final JSlider slider2;

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
        }
    }

    private class GrassPane extends JPanel implements ChangeListener {

        private final JSlider slider1;
        private final JSlider slider2;

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

            this.slider2.setValue((int) (Grass.NUTRITIONAL_VALUE_FACTOR * 100.0));

            sliderLabel = new JLabel("Nutritional Value", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


            this.add(sliderLabel);
            this.add(this.slider2);


            this.setVisible(true);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == this.slider1) {
                Grass.REPRODUCTION_THRESHOLD = this.slider1.getValue() / 100.0;
            }
            if (e.getSource() == this.slider2) {
                Grass.NUTRITIONAL_VALUE_FACTOR = this.slider2.getValue() / 100.0;
            }
        }
    }
}