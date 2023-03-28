/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.common.comps.ui;

import org.vpda.common.command.Command;
import org.vpda.common.comps.loc.AbstractButtonLocValue;
import org.vpda.common.comps.loc.IconLocValue;

/**
 * Abstract button component.
 * 
 * @author kitko
 * @param <T> - kind of ButtonLocValue we want to use
 *
 */
public abstract class AbstractButtonAC<T extends AbstractButtonLocValue> extends AbstractComponent<Boolean, T> {
    /** Command to execute when button is pressed */
    protected Command command;
    /** Label of button */
    protected String label;
    /** Button icon value */
    protected IconLocValue iconLocValue;
    /** Button disabled icon value */
    protected IconLocValue disabledIconLocValue;
    /** Mnemonic character */
    protected Character mnemonic;

    private static final long serialVersionUID = 1392053381491490871L;

    /**
     * @param id
     */
    protected AbstractButtonAC(String id) {
        super(id);
    }

    /** Creates button without id */
    protected AbstractButtonAC() {
    }

    /**
     * @param id
     * @param locValue
     * @param command
     */
    protected AbstractButtonAC(String id, T locValue, Command command) {
        super(id);
        if (locValue == null) {
            throw new IllegalArgumentException("locValue is null");
        }
        adjustFromLocValue(locValue);
        this.command = command;
    }

    /**
     * Sets action command fired when button is pressed
     * 
     * @param command
     * @return this
     */
    public AbstractButtonAC setCommand(Command command) {
        this.command = command;
        return this;

    }

    /**
     * @return command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * @return true if button is selected
     */
    public boolean isSelected() {
        return Boolean.TRUE.equals(getValue());
    }

    /**
     * Sets selected
     * 
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.setValue(Boolean.valueOf(selected));
    }

    /**
     * @return icon value
     */
    public IconLocValue getIconValue() {
        return iconLocValue;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public String getCaption() {
        return label;
    }

    @Override
    public void setCaption(String caption) {
        this.label = caption;
    }

    /**
     * Sets icon
     * 
     * @param icon
     */
    public void setIconValue(IconLocValue icon) {
        this.iconLocValue = icon;
    }

    /**
     * Sets label text
     * 
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return disabled icon
     */
    public IconLocValue getDisabledIconValue() {
        return disabledIconLocValue;
    }

    /**
     * Sets disabled icon
     * 
     * @param icon
     */
    public void setDisabledIconValue(IconLocValue icon) {
        this.disabledIconLocValue = icon;
    }

    @Override
    public void adjustFromLocValue(T locValue) {
        if (locValue == null) {
            return;
        }
        this.label = locValue.getLabel();
        this.iconLocValue = locValue.getIconValue();
        setTooltip(locValue.getTooltip());
        this.disabledIconLocValue = locValue.getDisabledIconValue();
        this.mnemonic = locValue.getMnemonic();
    }

    /**
     * @return the mnenomic
     */
    public Character getMnemonic() {
        return mnemonic;
    }

    /**
     * @param mnemonic the mnenomic to set
     */
    public void setMnemonic(Character mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public void assignValues(org.vpda.common.comps.ui.Component<Boolean, T> c) {
        AbstractButtonAC button = (AbstractButtonAC) c;
        super.assignValues(c);
        setCommand(button.getCommand());
        setSelected(button.isSelected());
        setLabel(button.getLabel());
        setIconValue(button.getIconValue());
        setDisabledIconValue(button.getDisabledIconValue());
        setMnemonic(button.getMnemonic());

    }

}
