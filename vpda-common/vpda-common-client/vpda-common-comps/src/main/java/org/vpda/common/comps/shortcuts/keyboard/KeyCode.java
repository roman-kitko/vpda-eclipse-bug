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
package org.vpda.common.comps.shortcuts.keyboard;

@SuppressWarnings("javadoc")
public enum KeyCode {

    F_F1(0), F_F2(1), F_F3(2), F_F4(3), F_F5(4), F_F6(5), F_F7(6), F_F8(7), F_F9(8), F_F10(9), F_F11(10), F_F12(11),

    S_ENTER, S_ESCAPE, S_PAGE_UP, S_PAGE_DOWN, S_TAB, S_ARROW_UP, S_ARROW_RIGHT, S_ARROW_LEFT, S_ARROW_DOWN, S_BACKSPACE, S_DELETE, S_INSERT, S_END, S_HOME,

    N_1(0), N_2(1), N_3(2), N_4(3), N_5(4), N_6(5), N_7(6), N_8(7), N_9(8),

    C_A(0), C_B(1), C_C(2), C_D(3), C_E(4), C_F(5), C_G(6), C_H(7), C_I(8), C_J(9), C_K(10), C_L(11), C_M(12), C_N(13), C_O(14), C_P(15), C_R(16), C_S(17), C_Q(18), C_T(19), C_U(20), C_V(21), C_W(22),
    C_X(23), C_Y(24), C_Z(25);

    KeyCode(int typeOrdinal) {
        this.typeOrdinal = typeOrdinal;
    }

    KeyCode() {
        this.typeOrdinal = 0;
    }

    private int typeOrdinal;

    /**
     * @return the typeOrdinal
     */
    public int getTypeOrdinal() {
        return typeOrdinal;
    }

}
