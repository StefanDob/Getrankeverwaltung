package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import de.tu.darmstadt.dataModel.Item;

public class ItemAdminDialog extends ItemDialog{

    public ItemAdminDialog(Item item) {
        super(item);

    }

    protected HorizontalLayout createHeader() {
        HorizontalLayout headerLayout =  super.createHeader();

        Button editButton = new Button("Edit");
        editButton.addClickListener(event -> {
            editButton.setText("Save");
            RichTextEditor editor = new RichTextEditor();
            editor.setValue(item.getDescription());
            contentLayout.remove(desciption);
            desciption = editor;
            contentLayout.add(desciption);
        });

        headerLayout.addComponentAtIndex(0,editButton);
        return headerLayout;
    }
}
