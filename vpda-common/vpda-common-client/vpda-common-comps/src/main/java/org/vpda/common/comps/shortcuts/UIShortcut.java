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
package org.vpda.common.comps.shortcuts;

import java.io.Serializable;

import org.vpda.common.command.Command;
import org.vpda.common.command.EnabledCommand;
import org.vpda.common.comps.Member;
import org.vpda.common.comps.MemberListenerSupportImpl;
import org.vpda.common.comps.MemberListenerSupportWithFireForSingleMember;
import org.vpda.common.types.EnabledObject;
import org.vpda.internal.common.util.Assert;

/**
 * @author kitko
 *
 */
public abstract class UIShortcut implements Serializable, EnabledObject, Member {
    private static final long serialVersionUID = 614040889380297012L;
    /** Id of shortcut */
    protected final String id;
    /** Command */
    protected EnabledCommand<?> command;
    private MemberListenerSupportImpl memberListenerSupport;
    private String permissionId;

    /**
     * @param id
     * @param command
     */
    @SuppressWarnings("unchecked")
    public UIShortcut(String id, Command<?> command) {
        super();
        this.id = Assert.isNotEmptyArgument(id, "id");
        this.command = new EnabledCommand(Assert.isNotNullArgument(command, "command"));
    }

    /**
     * @param id
     * @param command
     * @param enabled
     */
    @SuppressWarnings("unchecked")
    public UIShortcut(String id, Command<?> command, boolean enabled) {
        super();
        this.id = Assert.isNotEmptyArgument(id, "id");
        this.command = new EnabledCommand(Assert.isNotNullArgument(command, "command"), enabled);
    }

    /**
     * Sets command
     * 
     * @param cmd
     */
    @SuppressWarnings("unchecked")
    public void setCommand(Command cmd) {
        this.command = new EnabledCommand(Assert.isNotNullArgument(cmd, "command"), isEnabled());
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @return the cmd
     */
    public Command<?> getCommand() {
        return command;
    }

    @Override
    public boolean isEnabled() {
        return command.isEnabled();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        command.setEnabled(isEnabled);
    }

    /**
     * @param disabledOnce
     */
    public void setDisabledOnce(boolean disabledOnce) {
        command.setDisabledOnce(disabledOnce);
    }

    @Override
    public String getLocalId() {
        return id;
    }

    @Override
    public String getParentId() {
        return null;
    }

    /**
     * Sets permissionId
     * 
     * @param permissionId
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * @return permissionId
     */
    public String getPermissionId() {
        return permissionId;
    }

    @Override
    public MemberListenerSupportWithFireForSingleMember getMemberListenerSupport() {
        if (memberListenerSupport == null) {
            memberListenerSupport = new MemberListenerSupportImpl(this);
        }
        return memberListenerSupport;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UIShortcut other = (UIShortcut) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UIShortcut [id=" + id + "]";
    }

}
