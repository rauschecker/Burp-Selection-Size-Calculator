package burp;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {
    public static PrintWriter stdout;

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        // set our extension name
        callbacks.setExtensionName("Selection Size Calculator");
        BurpExtender.stdout = new PrintWriter(callbacks.getStdout(), true);
        callbacks.registerContextMenuFactory(this);
        BurpExtender.stdout.println("Selection Size Calculator loaded.");
        BurpExtender.stdout.println("Info: In case you want to determine the size of"+
        " a selection INCLUDING invisible characters, please enable \"Show non-printable chars\"");
    }

    @Override
    public List<JMenuItem> createMenuItems(final IContextMenuInvocation invocation) {
            int size = 0;
            int[] selectionBounds = invocation.getSelectionBounds();

            // if we have a valid selection AND the selection is not empty 
            // (e.g. caret set somewhere):
            if (selectionBounds != null && (selectionBounds[0] != selectionBounds[1])) {
                size = selectionBounds[1] - selectionBounds[0];

                final ArrayList<JMenuItem> menu_item_list = new ArrayList<JMenuItem>();
                final JMenuItem size_calculator_item = new JMenuItem("Selection: " + size + " bytes");
                menu_item_list.add(size_calculator_item);

                return menu_item_list;
            }
        return null;
    }
}