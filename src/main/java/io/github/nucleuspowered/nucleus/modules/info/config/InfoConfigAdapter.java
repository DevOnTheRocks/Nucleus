/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.info.config;

import com.google.common.reflect.TypeToken;
import io.github.nucleuspowered.nucleus.internal.qsml.NucleusConfigAdapter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class InfoConfigAdapter extends NucleusConfigAdapter<InfoConfig> {
    @Override
    protected InfoConfig getDefaultObject() {
        return new InfoConfig();
    }

    @Override
    protected InfoConfig convertFromConfigurateNode(ConfigurationNode node) throws ObjectMappingException {
        return node.getValue(TypeToken.of(InfoConfig.class), new InfoConfig());
    }

    @Override
    protected ConfigurationNode insertIntoConfigurateNode(InfoConfig data) throws ObjectMappingException {
        return SimpleCommentedConfigurationNode.root().setValue(TypeToken.of(InfoConfig.class), data);
    }
}
