package org.fundacionevangelica.reloj.clases;

import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 * 
 * Uses a combobox tooltip as the suggestion for auto complete and updates the
 * combo box itens accordingly <br />
 * It does not work with space, space and escape cause the combobox to hide and
 * clean the filter ... Send me a PR if you want it to work with all characters
 * -> It should be a custom controller - I KNOW!
 * 
 * @author wsiqueir
 *
 * @param <T>
 */
public class ComboBoxAutoComplete<T> {

	private ComboBox<T> cmb;
	String filter = "";
	private ObservableList<T> originalItems;
        private TextField enter_textfield;
        private Button enter_button;
        private String tipo_enter;
        public Integer id;

	public ComboBoxAutoComplete(ComboBox<T> cmb, TextField textField) {
		this.cmb = cmb;
                this.enter_textfield = textField;
		originalItems = FXCollections.observableArrayList(cmb.getItems());
		cmb.setTooltip(new Tooltip());
                tipo_enter = "textfield";
		cmb.setOnKeyPressed(this::handleOnKeyPressed);
		cmb.setOnHidden(this::handleOnHiding);
	}

	public ComboBoxAutoComplete(ComboBox<T> cmb, Button button) {
		this.cmb = cmb;
                this.enter_button = button;
		originalItems = FXCollections.observableArrayList(cmb.getItems());
		cmb.setTooltip(new Tooltip());
                tipo_enter = "button";
		cmb.setOnKeyPressed(this::handleOnKeyPressed);
		cmb.setOnHidden(this::handleOnHiding);
	}

        public void handleOnKeyPressed(KeyEvent e) {
		ObservableList<T> filteredList = FXCollections.observableArrayList();
		KeyCode code = e.getCode();

		if (code.isLetterKey()) {
			filter += e.getText();
		}
		if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
			filter = filter.substring(0, filter.length() - 1);
			cmb.getItems().setAll(originalItems);
		}
		if (code == KeyCode.ESCAPE) {
			filter = "";
		}
		if (code == KeyCode.ENTER) {
                    switch (this.tipo_enter) {
                        case "textfield":
                            enter_textfield.requestFocus();
                            break;
                        case "button":
                            enter_button.requestFocus();
                            break;
                    }
		}
                if (filter.length() == 0) {
			filteredList = originalItems;
			cmb.getTooltip().hide();
		} else {
			Stream<T> itens = cmb.getItems().stream();
			String txtUsr = filter.toString().toLowerCase();
			itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
			cmb.getTooltip().setText(txtUsr);
			Window stage = cmb.getScene().getWindow();
			double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
			double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
			cmb.getTooltip().show(stage, posX, posY);
			cmb.show();
		}
		cmb.getItems().setAll(filteredList);
	}

	public void handleOnHiding(Event e) {
		filter = "";
		cmb.getTooltip().hide();
		T s = cmb.getSelectionModel().getSelectedItem();
                String valor = s.toString();
                String[] split = valor.split(" - ");
                //System.out.println("Valor = " + split[0]);
                this.id = Integer.parseInt(split[0]);
                
		cmb.getItems().setAll(originalItems);
		cmb.getSelectionModel().select(s);
	}

}
