package de.tu.darmstadt.frontend.store;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextArea;
import de.tu.darmstadt.dataModel.Item;


public class ItemAdminDialog extends ItemDialog{

    public ItemAdminDialog(Item item) {
        super(item);

    }

    protected HorizontalLayout createHeader() {
        HorizontalLayout headerLayout =  super.createHeader();

        Button editButton = new Button("Edit");
        editButton.addClickListener(event -> {
            if(editButton.getText().equals("Edit")){
                editButton.setText("Save");
                TextArea editor = new TextArea();
                editor.setValue(item.getDescription());
                editor.setWidthFull();
                setDescription(editor);
            }else if(editButton.getText().equals("Save")){
                editButton.setText("Edit");
                setDescription(showDescription());
            }

        });

        headerLayout.addComponentAtIndex(0,editButton);
        return headerLayout;
    }
}
