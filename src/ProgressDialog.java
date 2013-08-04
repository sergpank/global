import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;

public class ProgressDialog  extends JDialog{

    private final JLabel compileStatusLabel;
    private final JLabel analyzeStatusLabel;
    private final JLabel printResultsStatusLabel;
    private final JProgressBar compileProgressBar;
    private final JProgressBar analyzeProgressBar;
    private final JProgressBar printResultsProgressBar;

    public ProgressDialog(JFrame parent) {
        super(parent, "Execution progress");
        getContentPane().setLayout(new FormLayout(buildColumns(), buildRows()));

        getContentPane().add(new JLabel("Compiling :"), "2, 2");

        compileProgressBar = new JProgressBar();
        getContentPane().add(compileProgressBar, "4, 2");

        compileStatusLabel = new JLabel("...");
        getContentPane().add(compileStatusLabel, "4, 4");

        getContentPane().add(new JLabel("Analyzing :"), "2, 6");

        analyzeProgressBar = new JProgressBar();
        getContentPane().add(analyzeProgressBar, "4, 6");

        analyzeStatusLabel = new JLabel("...");
        getContentPane().add(analyzeStatusLabel, "4, 8");

        getContentPane().add(new JLabel("Saving results :"), "2, 10");

        printResultsProgressBar = new JProgressBar();
        getContentPane().add(printResultsProgressBar, "4, 10");

        printResultsStatusLabel = new JLabel("...");
        getContentPane().add(printResultsStatusLabel, "4, 12");

        JButton excellentButton = new JButton("Exellent !");
        getContentPane().add(excellentButton, "2, 14");

        JButton exitButton = new JButton("Stop it !");
        getContentPane().add(exitButton, "4, 14, left, default");

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private RowSpec[] buildRows() {
        return new RowSpec[] {
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.UNRELATED_GAP_ROWSPEC,};
    }

    private ColumnSpec[] buildColumns() {
        return new ColumnSpec[] {
                FormFactory.UNRELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.UNRELATED_GAP_COLSPEC,
                ColumnSpec.decode("200dlu"),
                FormFactory.UNRELATED_GAP_COLSPEC,};
    }

    public void setMaxCompilingProgress(int value){
        compileProgressBar.setMaximum(value);
    }

    public void setCompileProgress(int progress){
        compileProgressBar.setValue(progress);
    }

    public void setCompileMessage(String message){
        compileStatusLabel.setText(message);
    }

    public void setMaxAnalysisProgress(int value){
        analyzeProgressBar.setMaximum(value);
    }

    public void setAnalysisProgress(int progress){
        analyzeProgressBar.setValue(progress);
    }

    public void setAnalysisMessage(String message){
        analyzeStatusLabel.setText(message);
    }

    public void setMaxReportingProgress(int value){
        printResultsProgressBar.setMaximum(value);
    }

    public void setReportingProgress(int progress){
        printResultsProgressBar.setValue(progress);
    }

    public void setReportingMessage(String message){
        printResultsStatusLabel.setText(message);
    }
}
