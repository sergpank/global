import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui {

    private JFrame frame;
    private JTextField inputPathField;
    private JButton inputPathButton;
    private JTextField outputPathField;
    private JButton outputPathButton;
    private JButton goButton;

    public Gui() {
        createGui();
        addListeners();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    private void createGui() {
        frame = new JFrame();
        frame.getContentPane().setLayout( new FormLayout( buildColumns(), buildRows() ) );

        inputPathField = new JTextField();
        outputPathField = new JTextField();
        inputPathButton = new JButton("...");
        outputPathButton = new JButton("...");
        goButton = new JButton("GO");

        frame.add(new JLabel("Source files directory:"), "2, 2, right, default");
        frame.add(inputPathField, "4, 2");
        frame.add(inputPathButton, "6, 2");

        frame.add(new JLabel("Output file:"), "2, 4, right, default");
        frame.add(outputPathField, "4, 4");
        frame.add(outputPathButton, "6, 4");

        frame.add(goButton, "4, 6, center, default");
    }

    private ColumnSpec[] buildColumns() {
        return new ColumnSpec[]{
                FormFactory.UNRELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.UNRELATED_GAP_COLSPEC,
                ColumnSpec.decode("150dlu"),
                FormFactory.UNRELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.UNRELATED_GAP_COLSPEC,};
    }

    private RowSpec[] buildRows() {
        return new RowSpec[]{
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,};
    }


    private void addListeners() {
        inputPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                int result = chooser.showDialog(null, "Get directory");
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedDirectory = chooser.getSelectedFile();
                    inputPathField.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });

        outputPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                int result = chooser.showDialog(null, "Write to this file");
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = chooser.getSelectedFile();
                    outputPathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalSearcher searcher = new GlobalSearcher(inputPathField.getText(), outputPathField.getText(), frame);
                searcher.execute();
            }
        });
    }
}
