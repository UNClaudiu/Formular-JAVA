package formular;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormularUI {
    private final JTextField numeField;
    private final JTextField prenumeField;
    private final JTextField taraField;
    private final JCheckBox impermeabilCheckBox;
    private final JRadioButton alergareRadioButton;
    private final JComboBox<String> marimeComboBox;
    private final JTextArea detaliiTextArea;
    public FormularUI() {

        JFrame frame = new JFrame("Formular Adidasi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new GridLayout(8, 4, 11, 11));

        frame.add(new JLabel("Nume:"));
        numeField = new JTextField();
        frame.add(numeField);

        frame.add(new JLabel("Prenume:"));
        prenumeField = new JTextField();
        frame.add(prenumeField);

        frame.add(new JLabel("Tara:"));
        taraField = new JTextField();
        frame.add(taraField);

        frame.add(new JLabel("Impermeabil:"));
        impermeabilCheckBox = new JCheckBox();
        frame.add(impermeabilCheckBox);

        frame.add(new JLabel("Tip sport:"));
        alergareRadioButton = new JRadioButton("Alergare");
        JRadioButton baschetRadioButton = new JRadioButton("Baschet");

        ButtonGroup sportButtonGroup = new ButtonGroup();
        sportButtonGroup.add(alergareRadioButton);
        sportButtonGroup.add(baschetRadioButton);
        JPanel sportPanel = new JPanel();
        sportPanel.add(alergareRadioButton);
        sportPanel.add(baschetRadioButton);
        frame.add(sportPanel);

        frame.add(new JLabel("Marime:"));
        String[] marimi = {"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"};
        marimeComboBox = new JComboBox<>(marimi);
        frame.add(marimeComboBox);

        frame.add(new JLabel("Detalii:"));
        detaliiTextArea = new JTextArea();
        JScrollPane detaliiScrollPane = new JScrollPane(detaliiTextArea);
        frame.add(detaliiScrollPane);

        JButton salvareButton = new JButton("Salvare");
        JButton anulareButton = new JButton("Anulare");

        salvareButton.addActionListener(e -> salvareInJSON());

        anulareButton.addActionListener(e -> System.exit(0));

        frame.add(salvareButton);
        frame.add(anulareButton);

        frame.setVisible(true);
    }
    private void salvareInJSON() {
        Map<String, Object> dateAdidasi = new HashMap<>();
        dateAdidasi.put("Nume", numeField.getText());
        dateAdidasi.put("Prenume", prenumeField.getText());
        dateAdidasi.put("Tara", taraField.getText());
        dateAdidasi.put("Impermeabil", impermeabilCheckBox.isSelected());
        dateAdidasi.put("TipSport", alergareRadioButton.isSelected() ? "Alergare" : "Baschet");
        dateAdidasi.put("Marime", marimeComboBox.getSelectedItem());
        dateAdidasi.put("Detalii", detaliiTextArea.getText());

        try (FileWriter fileWriter = new FileWriter("adidasi.json", true)) {
            fileWriter.write(transformaInJSON(dateAdidasi) + "\n");
            JOptionPane.showMessageDialog(null, "Date salvate cu succes!", "Salvare", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la salvare!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String transformaInJSON(Map<String, Object> date) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : date.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                jsonBuilder.append("\"").append(entry.getValue()).append("\"");
            } else {
                jsonBuilder.append(entry.getValue());
            }
            jsonBuilder.append(",");
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormularUI::new);
    }
}
