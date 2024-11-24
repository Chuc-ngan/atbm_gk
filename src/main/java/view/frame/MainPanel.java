package view.frame;

import view.component.*;
import view.tab.ClassicPanel;
import view.tab.FixedBottomPanel;
import view.tab.TabPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainPanel extends JPanel{

    private TabPanel tabPanel;
    private FixedBottomPanel fixedBottomPanel;

    public MainPanel(){
        setLayout(new BorderLayout());
        fixedBottomPanel =  new FixedBottomPanel();
        tabPanel =  new TabPanel(fixedBottomPanel);

        add(tabPanel, BorderLayout.CENTER);
        add(fixedBottomPanel, BorderLayout.SOUTH);

    }
}
