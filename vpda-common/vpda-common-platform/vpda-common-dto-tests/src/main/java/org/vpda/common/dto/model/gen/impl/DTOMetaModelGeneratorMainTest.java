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
package org.vpda.common.dto.model.gen.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.vpda.common.dto.annotations.DTOConcrete;
import org.vpda.common.dto.model.gen.MetaClassesGeneratorLogger;
import org.vpda.common.dto.model.gen.impl.DTOMetaModelGeneratorMain.Model;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInput;
import org.vpda.common.dto.runtime.DTORuntimeConfigurationInputFactory;
import org.vpda.common.dto.runtime.SingleUnitNameConfigurationInput;

public class DTOMetaModelGeneratorMainTest {

    @DTOConcrete
    static class MyDto {
        Long id;
    }

    public static final class MyDTORuntimeConfigurationInputFactory implements DTORuntimeConfigurationInputFactory {
        @Override
        public DTORuntimeConfigurationInput createInput() {
            return new SingleUnitNameConfigurationInput("myUnit", MyDto.class);
        }
    }

    private final File tmpDir = new File(System.getProperty("java.io.tmpdir"));

    @Test
    public void testMain() throws IOException {
        Model model = DTOMetaModelGeneratorMain.populateModel(new String[] { "--verbose", "--outputdir=" + tmpDir.getAbsolutePath() + "/DTOMetaModelGeneratorMainTest",
                "--factory=" + MyDTORuntimeConfigurationInputFactory.class.getName(), "--clean" });
        DTOMetaModelGeneratorMain.generateDTOs(model, MetaClassesGeneratorLogger.NoOptLoger.getInstance());
    }

    @Test
    public void testPopulate() {
        Model model = DTOMetaModelGeneratorMain.populateModel(new String[] { "--verbose", "--outputdir=" + tmpDir.getAbsolutePath() + "/DTOMetaModelGeneratorMainTest",
                "--factory=" + MyDTORuntimeConfigurationInputFactory.class.getName(), "--clean" });
        assertNotNull(model);
        assertEquals(true, model.isVerbose());
        assertEquals(true, model.isCleanOutputDir());
        assertNotNull(model.getOutputDir());
    }

}
